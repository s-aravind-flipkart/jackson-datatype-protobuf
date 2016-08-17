package com.hubspot.jackson.datatype.protobuf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class ProtobufDeserializer<T extends Message> extends StdDeserializer<MessageOrBuilder> {
  private final T defaultInstance;
  private final boolean build;
  @SuppressFBWarnings(value="SE_BAD_FIELD")
  private final Parser parser;

  public ProtobufDeserializer(Class<T> messageType, boolean build) throws JsonMappingException {
    this(messageType, build, JsonFormat.parser());
  }

  @SuppressWarnings("unchecked")
  public ProtobufDeserializer(Class<T> messageType, boolean build, Parser parser) throws JsonMappingException {
    super(messageType);

    try {
      this.defaultInstance = (T) messageType.getMethod("getDefaultInstance").invoke(null);
    } catch (Exception e) {
      throw new JsonMappingException("Unable to get default instance for type " + messageType, e);
    }

    this.build = build;
    this.parser = parser;
  }

  @Override
  public MessageOrBuilder deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    Message.Builder builder = defaultInstance.newBuilderForType();

    JsonToken token = parser.getCurrentToken();
    if (token == JsonToken.START_ARRAY) {
      token = parser.nextToken();
    }

    if (token == JsonToken.END_OBJECT) {
      return finish(builder);
    }

    Object inputSource = parser.getInputSource();
    final Reader reader;
    if (inputSource instanceof Reader) {
      reader = (Reader) inputSource;
    } else if (inputSource instanceof InputStream) {
      reader = new InputStreamReader((InputStream) inputSource, StandardCharsets.UTF_8);
    } else {
      reader = new StringReader(parser.readValueAsTree().toString());
    }

    try {
      this.parser.merge(reader, builder);
    } catch (InvalidProtocolBufferException e) {
      throw context.mappingException(e.getMessage());
    }

    return finish(builder);
  }

  private MessageOrBuilder finish(Message.Builder builder) {
    if (build) {
      return builder.build();
    } else {
      return builder;
    }
  }
}

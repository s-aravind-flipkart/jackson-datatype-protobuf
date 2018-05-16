package com.hubspot.jackson.datatype.protobuf;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.os72.protobuf351.Message;


public abstract class BuildingDeserializer<T extends Message, V extends Message.Builder> extends StdDeserializer<T> {

  protected BuildingDeserializer(Class<T> messageType) {
    super(messageType);
  }

  public abstract JsonDeserializer<V> getWrappedDeserializer();

  @Override
  @SuppressWarnings("unchecked")
  public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    return (T) getWrappedDeserializer().deserialize(parser, context).build();
  }

  @Override
  @SuppressWarnings("unchecked")
  public T getNullValue(DeserializationContext context) throws JsonMappingException {
    Message.Builder builder = getWrappedDeserializer().getNullValue(context);
    return builder == null ? null : (T) builder.build();
  }
}

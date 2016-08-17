package com.hubspot.jackson.datatype.protobuf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Printer;

import java.io.IOException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class ProtobufSerializer extends StdSerializer<MessageOrBuilder> {
  @SuppressFBWarnings(value="SE_BAD_FIELD")
  private final Printer printer;

  public ProtobufSerializer() {
    this(JsonFormat.printer());
  }

  public ProtobufSerializer(Printer printer) {
    super(MessageOrBuilder.class);
    this.printer = printer;
  }

  @Override
  public void serialize(MessageOrBuilder message, JsonGenerator generator, SerializerProvider serializerProvider)
          throws IOException {
    try {
      // just to see if it supports writeRaw
      generator.writeRaw("");
      writeFast(message, generator);
    } catch (UnsupportedOperationException e) {
      writeSlow(message, generator);
    }
  }

  private void writeFast(MessageOrBuilder message, final JsonGenerator generator) throws IOException {
    printer.appendTo(message, new Appendable() {

      @Override
      public Appendable append(CharSequence csq) throws IOException {
        generator.writeRaw(csq.toString());
        return this;
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        return append(csq.subSequence(start, end));
      }

      @Override
      public Appendable append(char c) throws IOException {
        generator.writeRaw(c);
        return this;
      }
    });
  }

  private void writeSlow(MessageOrBuilder message, JsonGenerator generator) throws IOException {
    String json = printer.print(message);

    ObjectCodec codec = generator.getCodec();
    JsonParser parser = codec.getFactory().createParser(json);
    generator.writeTree(codec.readTree(parser));
  }
}

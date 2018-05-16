package com.hubspot.jackson.datatype.protobuf.builtin.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.os72.protobuf351.Descriptors.FieldDescriptor;
import com.github.os72.protobuf351.Message;
import com.github.os72.protobuf351.Message.Builder;
import com.hubspot.jackson.datatype.protobuf.ProtobufDeserializer;

public class WrappedPrimitiveDeserializer<T extends Message, V extends Builder> extends ProtobufDeserializer<T, V> {

  public WrappedPrimitiveDeserializer(Class<T> wrapperType) {
    super(wrapperType);
  }

  @Override
  protected void populate(V builder, JsonParser parser, DeserializationContext context) throws IOException {
    FieldDescriptor field = builder.getDescriptorForType().findFieldByName("value");
    Object value = readValue(builder, field, null, parser, context);
    builder.setField(field, value);
  }
}

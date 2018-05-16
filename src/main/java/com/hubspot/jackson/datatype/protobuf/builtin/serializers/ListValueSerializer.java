package com.hubspot.jackson.datatype.protobuf.builtin.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.os72.protobuf351.Descriptors.FieldDescriptor;
import com.github.os72.protobuf351.ListValue;
import com.github.os72.protobuf351.Value;
import com.hubspot.jackson.datatype.protobuf.ProtobufSerializer;

public class ListValueSerializer extends ProtobufSerializer<ListValue> {
  private static final FieldDescriptor VALUES_FIELD = ListValue.getDescriptor().findFieldByName("values");

  public ListValueSerializer() {
    super(ListValue.class);
  }

  @Override
  public void serialize(
          ListValue listValue,
          JsonGenerator generator,
          SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeStartArray();
    for (Value value : listValue.getValuesList()) {
      writeValue(VALUES_FIELD, value, generator, serializerProvider);
    }
    generator.writeEndArray();
  }
}

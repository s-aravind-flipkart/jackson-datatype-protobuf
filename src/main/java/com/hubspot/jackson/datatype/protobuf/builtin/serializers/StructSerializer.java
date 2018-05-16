package com.hubspot.jackson.datatype.protobuf.builtin.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.os72.protobuf351.Descriptors.FieldDescriptor;
import com.github.os72.protobuf351.Struct;
import com.hubspot.jackson.datatype.protobuf.ProtobufSerializer;

public class StructSerializer extends ProtobufSerializer<Struct> {
  private static final FieldDescriptor FIELDS_FIELD = Struct.getDescriptor().findFieldByName("fields");

  public StructSerializer() {
    super(Struct.class);
  }

  @Override
  public void serialize(
          Struct struct,
          JsonGenerator generator,
          SerializerProvider serializerProvider
  ) throws IOException {
    writeMap(FIELDS_FIELD, struct.getField(FIELDS_FIELD), generator, serializerProvider);
  }
}

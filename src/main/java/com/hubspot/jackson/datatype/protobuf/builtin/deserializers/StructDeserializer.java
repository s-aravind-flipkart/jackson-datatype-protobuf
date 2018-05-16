package com.hubspot.jackson.datatype.protobuf.builtin.deserializers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.os72.protobuf351.Descriptors.FieldDescriptor;
import com.github.os72.protobuf351.Message;
import com.github.os72.protobuf351.Struct;
import com.hubspot.jackson.datatype.protobuf.ProtobufDeserializer;

public class StructDeserializer extends ProtobufDeserializer<Struct, Struct.Builder> {
  private static final FieldDescriptor FIELDS_FIELD = Struct.getDescriptor().findFieldByName("fields");

  public StructDeserializer() {
    super(Struct.class);
  }

  @Override
  protected void populate(
          Struct.Builder builder,
          JsonParser parser,
          DeserializationContext context
  ) throws IOException {
    List<Message> entries = readMap(builder, FIELDS_FIELD, parser, context);
    for (Message entry : entries) {
      builder.addRepeatedField(FIELDS_FIELD, entry);
    }
  }
}

package com.hubspot.jackson.datatype.protobuf.builtin.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.os72.protobuf351.FieldMask;
import com.github.os72.protobuf351.util.FieldMaskUtil;
import com.hubspot.jackson.datatype.protobuf.ProtobufSerializer;

public class FieldMaskSerializer extends ProtobufSerializer<FieldMask> {

  public FieldMaskSerializer() {
    super(FieldMask.class);
  }

  @Override
  public void serialize(
          FieldMask fieldMask,
          JsonGenerator generator,
          SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeString(FieldMaskUtil.toJsonString(fieldMask));
  }
}

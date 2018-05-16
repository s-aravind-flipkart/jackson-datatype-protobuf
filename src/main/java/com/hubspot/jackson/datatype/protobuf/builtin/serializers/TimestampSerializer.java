package com.hubspot.jackson.datatype.protobuf.builtin.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.os72.protobuf351.Timestamp;
import com.github.os72.protobuf351.util.Timestamps;
import com.hubspot.jackson.datatype.protobuf.ProtobufSerializer;

public class TimestampSerializer extends ProtobufSerializer<Timestamp> {

  public TimestampSerializer() {
    super(Timestamp.class);
  }

  @Override
  public void serialize(
          Timestamp timestamp,
          JsonGenerator generator,
          SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeString(Timestamps.toString(timestamp));
  }
}

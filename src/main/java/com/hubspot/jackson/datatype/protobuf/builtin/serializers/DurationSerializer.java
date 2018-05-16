package com.hubspot.jackson.datatype.protobuf.builtin.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.os72.protobuf351.Duration;
import com.github.os72.protobuf351.util.Durations;
import com.hubspot.jackson.datatype.protobuf.ProtobufSerializer;

public class DurationSerializer extends ProtobufSerializer<Duration> {

  public DurationSerializer() {
    super(Duration.class);
  }

  @Override
  public void serialize(
          Duration duration,
          JsonGenerator generator,
          SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeString(Durations.toString(duration));
  }
}

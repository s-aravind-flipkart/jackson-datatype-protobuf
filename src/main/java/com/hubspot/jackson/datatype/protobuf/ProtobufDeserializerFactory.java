package com.hubspot.jackson.datatype.protobuf;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;

public class ProtobufDeserializerFactory extends Deserializers.Base {
  private final Parser parser;

  public ProtobufDeserializerFactory() {
    this(JsonFormat.parser());
  }

  public ProtobufDeserializerFactory(Parser parser) {
    this.parser = parser;
  }

  @Override
  @SuppressWarnings("unchecked")
  public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc)
          throws JsonMappingException {
    if (Message.class.isAssignableFrom(type.getRawClass())) {
      return getDeserializer((Class<? extends Message>) type.getRawClass(), true);
    } else if (Message.Builder.class.isAssignableFrom(type.getRawClass())) {
      return getDeserializer((Class<? extends Message>) type.getRawClass().getDeclaringClass(), false);
    } else {
      return super.findBeanDeserializer(type, config, beanDesc);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends Message> ProtobufDeserializer<T> getDeserializer(Class<T> messageType, boolean build)
          throws JsonMappingException {
    return new ProtobufDeserializer<>(messageType, build, parser);
  }
}

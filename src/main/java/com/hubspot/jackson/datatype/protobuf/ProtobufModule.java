package com.hubspot.jackson.datatype.protobuf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;
import com.google.protobuf.util.JsonFormat.Printer;

/**
 * Module to add support for reading and writing Protobufs
 *
 * Register with Jackson via {@link com.fasterxml.jackson.databind.ObjectMapper#registerModule}
 */
public class ProtobufModule extends Module {
  private final Printer printer;
  private final Parser parser;

  public ProtobufModule() {
    this(JsonFormat.printer());
  }

  public ProtobufModule(Printer printer) {
    this(printer, JsonFormat.parser());
  }

  public ProtobufModule(Parser parser) {
    this(JsonFormat.printer(), parser);
  }

  public ProtobufModule(Printer printer, Parser parser) {
    this.printer = printer;
    this.parser = parser;
  }

  @Override
  public String getModuleName() {
    return "ProtobufModule";
  }

  @Override
  public Version version() {
    return ModuleVersion.instance.version();
  }

  @Override
  public void setupModule(SetupContext context) {
    SimpleSerializers serializers = new SimpleSerializers();
    serializers.addSerializer(new ProtobufSerializer(printer));

    context.addSerializers(serializers);
    context.addDeserializers(new ProtobufDeserializerFactory(parser));
    context.setMixInAnnotations(MessageOrBuilder.class, MessageOrBuilderMixin.class);
  }

  @JsonAutoDetect(getterVisibility = Visibility.NONE,
                  isGetterVisibility =  Visibility.NONE,
                  setterVisibility =  Visibility.NONE,
                  creatorVisibility = Visibility.NONE,
                  fieldVisibility = Visibility.NONE)
  private static class MessageOrBuilderMixin { }
}

package com.hubspot.jackson.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.util.JsonFormat;
import com.hubspot.jackson.test.util.TestProtobuf.AllFields;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static com.hubspot.jackson.test.util.ObjectMapperHelper.custom;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonInclusionTest {
  private static Set<String> allNonMessageFields;
  private static Set<String> arrayFields;

  @BeforeClass
  public static void setup() {
    allNonMessageFields = new HashSet<>();
    arrayFields = new HashSet<>();

    Descriptor descriptor = AllFields.getDescriptor();
    for (FieldDescriptor field : descriptor.getFields()) {
      if (field.getJavaType() != FieldDescriptor.JavaType.MESSAGE) {
        allNonMessageFields.add(field.getName());
      }

      if (field.isRepeated()) {
        arrayFields.add(field.getName());
      }
    }
  }

  @Test
  public void itWritesMissingFieldsAsNullWhenSerializationIncludeIsAlways() {
    AllFields message = AllFields.getDefaultInstance();

    JsonNode node = includeDefaults().valueToTree(message);

    for (String field : allNonMessageFields) {
      assertThat(node.has(field)).isTrue();
      if (arrayFields.contains(field)) {
        assertThat(node.get(field).isArray());
      } else {
        assertThat(node.get(field).isNull());
      }
    }
  }

  @Test
  public void itOnlyWritesArrayFieldsWhenSerializationIncludeIsNotAlways() {
    AllFields message = AllFields.getDefaultInstance();

    JsonNode node = excludeDefaults().valueToTree(message);

    for (String field : allNonMessageFields) {
      if (arrayFields.contains(field)) {
        assertThat(node.has(field)).isTrue();
        assertThat(node.get(field).isArray());
      } else {
        assertThat(node.has(field)).isFalse();
      }
    }
  }

  private static ObjectMapper includeDefaults() {
    return custom(JsonFormat.printer().includingDefaultValueFields());
  }

  private static ObjectMapper excludeDefaults() {
    return camelCase();
  }
}

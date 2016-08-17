package com.hubspot.jackson.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.test.util.TestProtobuf.RepeatedFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class WriteEmptyArraysTest {

  @Test
  public void testDisabled() {
    ObjectMapper mapper = camelCase();

    JsonNode node = mapper.valueToTree(getObject());
    assertThat(node.has("bool")).isFalse();
  }

  private static RepeatedFields getObject() {
    return RepeatedFields.newBuilder().build();
  }
}

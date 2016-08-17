package com.hubspot.jackson.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.test.util.TestProtobuf.RepeatedFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class WriteSingleElementArraysUnwrappedTest {

  @Test
  public void testDisabled() {
    ObjectMapper mapper = camelCase();

    JsonNode node = mapper.valueToTree(getObject());
    assertThat(node.has("bool")).isTrue();
    assertThat(node.get("bool").isArray()).isTrue();
    assertThat(node.get("bool").size()).isEqualTo(1);
    assertThat(node.get("bool").get(0).isBoolean()).isTrue();
    assertThat(node.get("bool").get(0).booleanValue()).isFalse();
  }

  private static RepeatedFields getObject() {
    return RepeatedFields.newBuilder().addBool(false).build();
  }
}

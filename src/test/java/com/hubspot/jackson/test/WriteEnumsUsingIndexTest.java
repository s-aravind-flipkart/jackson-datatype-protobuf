package com.hubspot.jackson.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.test.util.TestProtobuf;
import com.hubspot.jackson.test.util.TestProtobuf.AllFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class WriteEnumsUsingIndexTest {

  @Test
  public void testDisabled() {
    ObjectMapper mapper = camelCase();

    JsonNode node = mapper.valueToTree(getObject());
    assertThat(node.has("enum")).isTrue();
    assertThat(node.get("enum").isTextual()).isTrue();
    assertThat(node.get("enum").textValue()).isEqualTo("TWO");
  }

  private static AllFields getObject() {
    return AllFields.newBuilder().setEnum(TestProtobuf.Enum.TWO).build();
  }
}

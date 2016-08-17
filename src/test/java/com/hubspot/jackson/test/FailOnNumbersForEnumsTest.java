package com.hubspot.jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.test.util.TestProtobuf;
import com.hubspot.jackson.test.util.TestProtobuf.AllFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class FailOnNumbersForEnumsTest {

  @Test
  public void testDisabled() throws JsonProcessingException {
    ObjectMapper mapper = camelCase();

    AllFields parsed = mapper.treeToValue(buildNode(), AllFields.class);
    assertThat(parsed.getEnum()).isEqualTo(TestProtobuf.Enum.TWO);
  }

  private static JsonNode buildNode() {
    return camelCase().createObjectNode().put("enum", 2);
  }
}

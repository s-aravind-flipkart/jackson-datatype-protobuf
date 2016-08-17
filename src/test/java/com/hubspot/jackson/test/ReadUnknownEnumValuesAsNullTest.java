package com.hubspot.jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.test.util.TestProtobuf;
import com.hubspot.jackson.test.util.TestProtobuf.AllFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class ReadUnknownEnumValuesAsNullTest {

  @Test(expected = JsonMappingException.class)
  public void testStringDisabled() throws JsonProcessingException {
    ObjectMapper mapper = camelCase();

    mapper.treeToValue(buildNode("fakeValue"), AllFields.class);
  }

  @Test
  public void testIntDisabled() throws JsonProcessingException {
    ObjectMapper mapper = camelCase();

    AllFields parsed = mapper.treeToValue(buildNode(999999), AllFields.class);
    assertThat(parsed.getEnum()).isEqualTo(TestProtobuf.Enum.UNRECOGNIZED);
  }

  private static JsonNode buildNode(String value) {
    return camelCase().createObjectNode().put("enum", value);
  }

  private static JsonNode buildNode(int value) {
    return camelCase().createObjectNode().put("enum", value);
  }
}

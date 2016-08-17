package com.hubspot.jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hubspot.jackson.test.util.TestProtobuf.AllFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;

public class FailOnUnknownPropertiesTest {

  @Test(expected = JsonMappingException.class)
  public void testEnabled() throws JsonProcessingException {
    ObjectMapper mapper = camelCase();

    mapper.treeToValue(buildNode(), AllFields.class);
  }

  private static JsonNode buildNode() {
    ObjectNode node = camelCase().createObjectNode();

    addObject(node);
    addArray(node);

    return node;
  }

  private static void addObject(ObjectNode node) {
    node.putObject("fakeObject").put("fakeProperty", true);
  }

  private static void addArray(ObjectNode node) {
    node.putArray("fakeArray").add(true);
  }
}

package com.hubspot.jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.test.util.TestProtobuf.RepeatedFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;

public class AcceptSingleValueAsArrayTest {

  @Test(expected = JsonMappingException.class)
  public void testDisabled() throws JsonProcessingException {
    ObjectMapper mapper = camelCase();

    mapper.treeToValue(buildNode(), RepeatedFields.class);
  }

  private static JsonNode buildNode() {
    return camelCase().createObjectNode().put("bool", true);
  }
}

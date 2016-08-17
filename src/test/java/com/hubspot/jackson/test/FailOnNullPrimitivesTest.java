package com.hubspot.jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hubspot.jackson.test.util.TestProtobuf.AllFields;
import org.junit.Test;

import static com.hubspot.jackson.test.util.ObjectMapperHelper.camelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class FailOnNullPrimitivesTest {

  @Test
  public void testIntDisabled() throws JsonProcessingException {
    ObjectNode node = buildNode("int32");

    AllFields parsed = camelCase().treeToValue(node, AllFields.class);
    assertThat(camelCase().valueToTree(parsed).size()).isEqualTo(0);
  }

  @Test
  public void testLongDisabled() throws JsonProcessingException {
    ObjectNode node = buildNode("int64");

    AllFields parsed = camelCase().treeToValue(node, AllFields.class);
    assertThat(camelCase().valueToTree(parsed).size()).isEqualTo(0);
  }

  @Test
  public void testFloatDisabled() throws JsonProcessingException {
    ObjectNode node = buildNode("float");

    AllFields parsed = camelCase().treeToValue(node, AllFields.class);
    assertThat(camelCase().valueToTree(parsed).size()).isEqualTo(0);
  }

  @Test
  public void tesDoubleDisabled() throws JsonProcessingException {
    ObjectNode node = buildNode("double");

    AllFields parsed = camelCase().treeToValue(node, AllFields.class);
    assertThat(camelCase().valueToTree(parsed).size()).isEqualTo(0);
  }

  @Test
  public void testBooleanDisabled() throws JsonProcessingException {
    ObjectNode node = buildNode("bool");

    AllFields parsed = camelCase().treeToValue(node, AllFields.class);
    assertThat(camelCase().valueToTree(parsed).size()).isEqualTo(0);
  }

  @Test
  public void testOnlyAffectsPrimitives() throws JsonProcessingException {
    ObjectNode node = buildNode("string", "bytes", "enum", "nested");

    AllFields parsed = camelCase().treeToValue(node, AllFields.class);
    assertThat(camelCase().valueToTree(parsed).size()).isEqualTo(0);
  }

  private ObjectNode buildNode(String... fieldNames) {
    ObjectNode node = camelCase().createObjectNode();

    for (String fieldName : fieldNames) {
      node.putNull(fieldName);
    }

    return node;
  }
}

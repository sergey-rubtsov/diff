package com.vaes.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtils {

    public static JsonNode getTestNode(String fileName) throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        return new ObjectMapper().readValue(classLoader.getResourceAsStream(fileName),
                new TypeReference<JsonNode>() {
                });
    }

}

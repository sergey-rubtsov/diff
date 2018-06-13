package com.vaes.json.domain.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaes.json.exception.InternalServerError;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

/**
 * This converter is needed to prepare {@link JsonNode} for serialisation
 * and storing in database. Because Hibernate can't store custom fields classes.
 */
@Converter
public class JsonConverter implements AttributeConverter<JsonNode, String> {

    private ObjectMapper mapper = new ObjectMapper();

    public String convertToDatabaseColumn(JsonNode attribute) {
        return attribute.toString();
    }

    public JsonNode convertToEntityAttribute(String dbData) {
        try {
            return mapper.readTree(dbData);
        } catch (IOException e) {
            throw new InternalServerError();
        }
    }
}

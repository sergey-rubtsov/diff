package com.vaes.json.domain.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaes.json.domain.repository.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class JsonObject {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid;

    @Column(nullable = false)
    private Type type;

    @Column(length = 2_147_483_647)
    @Convert(converter = JsonConverter.class)
    @Lob
    private JsonNode json;

    /**
     * @param json object stores json
     * @param type type of object, can be LEFT or RIGHT or DIFF
     * @param uid  the identifier, must be unique
     */
    public JsonObject(JsonNode json, Type type, String uid) {
        this.uid = uid;
        this.type = type;
        this.json = json;
    }
}

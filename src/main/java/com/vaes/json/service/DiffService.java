package com.vaes.json.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;
import com.vaes.json.domain.model.JsonObject;
import com.vaes.json.domain.model.Type;
import com.vaes.json.domain.repository.JsonRepository;
import com.vaes.json.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiffService {

    @Autowired
    private JsonRepository jsonRepository;

    /**
     * @param uid identifier
     *
     * @return result {@link JsonNode} instance
     */
    public JsonNode getDiff(String uid) {
        return jsonRepository.findJsonObjectByUid(uid).map(JsonObject::getJson)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Here the new posted JSON is processed.
     * First, there is an attempt to find JSON by uid in the database.
     * If it is found, it's type is checked.
     * If the type is the same, JSON is updated in db only.
     * If the type not is the same, the diff is calculated and
     * stored in db with that uid.
     * If there is no JSON with that uid, the new entity created and stored in db.
     * @param json new JsonNode from controller
     * @param type type of JSON, LEFT or RIGHT or DIFF
     * @param uid  identifier
     */
    public void processJson(JsonNode json, Type type, String uid) {
        Optional<JsonObject> found = jsonRepository.findJsonObjectByUid(uid);
        JsonObject updated = found.map(process -> {
                    if (process.getType() == type) {
                        return process;
                    }
                    return calculateDiff(process, json);
                }
        ).orElse(buildJsonObject(json, type, uid));
        jsonRepository.save(updated);
    }

    /**
     * @param source {@link JsonObject} instance must contain valid JSON object or
     *               array or value, type (LEFT or RIGHT) and identifier.
     * @param target must be valid JSON object or array or value.
     *
     * @return computes and returns an updated {@link JsonObject} instance,
     * contains semantic diff JSON (JSON patch) from source to target.
     */
    private JsonObject calculateDiff(JsonObject source, JsonNode target) {
        JsonNode left;
        JsonNode right;
        if (source.getType() == Type.LEFT) {
            left = source.getJson();
            right = target;
        } else {
            left = target;
            right = source.getJson();
        }
        source.setType(Type.DIFF);
        source.setJson(JsonDiff.asJson(left, right));
        return source;
    }

    /**
     * @param json new JsonNode
     * @param type can be LEFT or RIGHT
     * @param uid  identifier to store it in db and retrieve
     *
     * @return new {@link JsonObject} instance to store it in db
     */
    private JsonObject buildJsonObject(JsonNode json, Type type, String uid) {
        return new JsonObject(json, type, uid);
    }

}

package com.vaes.json.domain.repository;

import com.vaes.json.domain.model.JsonObject;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository to manage {@link JsonObject} instances.
 */
public interface JsonRepository extends Repository<JsonObject, Long> {

    /**
     * @param uid identifier
     *
     * @return optional unique {@link JsonObject} instance.
     */
    public Optional<JsonObject> findJsonObjectByUid(String uid);

    /**
     * Saves the given {@link JsonObject}
     *
     * @param jsonObject
     */
    public void save(JsonObject jsonObject);
}

package com.vaes.json.domain.repository;

import com.vaes.json.domain.model.Base64Data;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository to manage {@link Base64Data} instances.
 */
public interface JsonRepository extends Repository<Base64Data, Long> {

    /**
     * @param uid identifier
     *
     * @return optional unique {@link Base64Data} instance.
     */
    public Optional<Base64Data> findJsonObjectByUid(String uid);

    /**
     * Saves the given {@link Base64Data}
     *
     * @param base64Data
     */
    public void save(Base64Data base64Data);
}

package com.vaes.json.domain.repository;

import com.vaes.json.TestUtils;
import com.vaes.json.domain.model.JsonObject;
import com.vaes.json.domain.model.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * integration test for database
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonRepositoryTest {

    @Autowired
    private JsonRepository jsonRepository;

    @Test
    public void saveAndFindJsonObjectByUid() throws IOException {
        JsonObject json = new JsonObject(TestUtils.getTestNode("left.json"), Type.LEFT, "test");
        Optional<JsonObject> found = jsonRepository.findJsonObjectByUid("test");
        assertFalse(found.isPresent());
        jsonRepository.save(json);
        found = jsonRepository.findJsonObjectByUid("test");
        assertTrue(found.isPresent());
        JsonObject result = found.get();
        assertEquals(json, result);
    }

}
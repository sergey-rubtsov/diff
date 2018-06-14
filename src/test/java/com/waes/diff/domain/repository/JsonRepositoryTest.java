package com.waes.diff.domain.repository;

import com.waes.diff.TestUtils;
import com.waes.diff.domain.model.Base64Data;
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
        Base64Data json = new Base64Data(TestUtils.getTestNode("left.txt"), TestUtils.getTestNode("right.txt"), "test");
        Optional<Base64Data> found = jsonRepository.findJsonObjectByUid("test");
        assertFalse(found.isPresent());
        jsonRepository.save(json);
        found = jsonRepository.findJsonObjectByUid("test");
        assertTrue(found.isPresent());
        Base64Data result = found.get();
        assertEquals(json, result);
    }

}
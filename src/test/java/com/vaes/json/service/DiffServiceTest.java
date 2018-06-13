package com.vaes.json.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaes.json.TestUtils;
import com.vaes.json.domain.model.JsonObject;
import com.vaes.json.domain.model.Type;
import com.vaes.json.domain.repository.JsonRepository;
import com.vaes.json.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceTest {

    @InjectMocks
    private DiffService diffService;

    @Mock
    private JsonRepository jsonRepository;

    @Test
    public void getDiff() throws IOException {
        JsonNode expected = TestUtils.getTestNode("result.json");
        Optional<JsonObject> stored = Optional.of(new JsonObject(expected, Type.LEFT, "id"));
        when(jsonRepository.findJsonObjectByUid(anyString())).thenReturn(stored);

        JsonNode result = diffService.getDiff("id");

        verify(jsonRepository).findJsonObjectByUid(eq("id"));
        assertEquals(expected, result);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getDiffNotFound() {
        Optional<JsonObject> stored = Optional.empty();
        when(jsonRepository.findJsonObjectByUid(anyString())).thenReturn(stored);

        diffService.getDiff("id");
    }

    @Test
    public void processJson() throws IOException {
        JsonNode left = TestUtils.getTestNode("left.json");
        Optional<JsonObject> stored = Optional.of(new JsonObject(left, Type.LEFT, "id"));
        when(jsonRepository.findJsonObjectByUid(anyString())).thenReturn(stored);
        doNothing().when(jsonRepository).save(any(JsonObject.class));
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        JsonNode right = TestUtils.getTestNode("right.json");

        diffService.processJson(right, Type.RIGHT, "id");

        verify(jsonRepository).findJsonObjectByUid(eq("id"));
        verify(jsonRepository).save(captor.capture());
        JsonObject result = captor.getValue();
        JsonNode expected = TestUtils.getTestNode("result.json");
        assertEquals(expected.toString(), result.getJson().toString());
    }

}
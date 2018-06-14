package com.waes.diff.service;

import com.waes.diff.TestUtils;
import com.waes.diff.domain.model.Base64Data;
import com.waes.diff.domain.model.Type;
import com.waes.diff.domain.repository.JsonRepository;
import com.waes.diff.exception.ResourceNotFoundException;
import com.waes.diff.message.StatusMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        String left = TestUtils.getTestNode("left.txt");
        String right = TestUtils.getTestNode("right.txt");
        Optional<Base64Data> stored = Optional.of(new Base64Data(left, right, "id"));
        when(jsonRepository.findJsonObjectByUid(anyString())).thenReturn(stored);

        StatusMessage result = diffService.getDiff("id");

        verify(jsonRepository).findJsonObjectByUid(eq("id"));

        assertEquals(1024, (int) result.getSize());
        assertEquals(1009, (int) result.getOffset());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getDiffNotFound() {
        Optional<Base64Data> stored = Optional.empty();
        when(jsonRepository.findJsonObjectByUid(anyString())).thenReturn(stored);

        diffService.getDiff("id");
    }

    @Test
    public void processJson() throws IOException {
        String left = TestUtils.getTestNode("left.txt");
        String right = TestUtils.getTestNode("right.txt");
        Optional<Base64Data> stored = Optional.of(new Base64Data(left, right, "id"));
        when(jsonRepository.findJsonObjectByUid(anyString())).thenReturn(stored);
        doNothing().when(jsonRepository).save(any(Base64Data.class));
        ArgumentCaptor<Base64Data> captor = ArgumentCaptor.forClass(Base64Data.class);

        diffService.storeData(right, Type.RIGHT, "id");

        verify(jsonRepository).findJsonObjectByUid(eq("id"));
        verify(jsonRepository).save(captor.capture());
        Base64Data result = captor.getValue();
        assertTrue(result.getLeft().isPresent());
        assertTrue(result.getRight().isPresent());
        assertEquals(left, result.getLeft().get());
        assertEquals(right, result.getRight().get());
    }

}
package com.vaes.json.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaes.json.DiffApplication;
import com.vaes.json.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DiffApplication.class)
@WebAppConfiguration
public class DiffControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testUsualScenario() throws Exception {
        String leftJson = TestUtils.getTestNode("left.txt");
        String rightJson = TestUtils.getTestNode("right.txt");
        URI leftUri = URI.create("/v1/diff/42/left");
        this.mockMvc.perform(MockMvcRequestBuilders.post(leftUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftJson))
                .andExpect(status().isOk());
        URI rightUri = URI.create("/v1/diff/42/right");
        this.mockMvc.perform(MockMvcRequestBuilders.post(rightUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(rightJson))
                .andExpect(status().isOk());
        URI diff = URI.create("/v1/diff/42");
        this.mockMvc.perform(get(diff)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"size\":1024,\"offset\":1009}", true));
    }

    @Test
    public void testOppositeScenario() throws Exception {
        String leftJson = TestUtils.getTestNode("left.txt");
        String rightJson = TestUtils.getTestNode("right.txt");
        URI rightUri = URI.create("/v1/diff/24/right");
        this.mockMvc.perform(MockMvcRequestBuilders.post(rightUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(rightJson))
                .andExpect(status().isOk());
        URI leftUri = URI.create("/v1/diff/24/left");
        this.mockMvc.perform(MockMvcRequestBuilders.post(leftUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftJson))
                .andExpect(status().isOk());
        URI diff = URI.create("/v1/diff/24");
        this.mockMvc.perform(get(diff)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"size\":1024,\"offset\":1009}", true));
    }

    @Test
    public void testEqualsAndDiffSizeScenario() throws Exception {
        String leftJson = TestUtils.getTestNode("left.txt");
        String rightJson = TestUtils.getTestNode("left.txt");
        URI rightUri = URI.create("/v1/diff/25/right");
        URI leftUri = URI.create("/v1/diff/25/left");
        this.mockMvc.perform(MockMvcRequestBuilders.post(leftUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftJson))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.post(rightUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(rightJson))
                .andExpect(status().isOk());
        URI diff = URI.create("/v1/diff/25");
        this.mockMvc.perform(get(diff)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"EQUAL_SIZE\"}", true));
        this.mockMvc.perform(MockMvcRequestBuilders.post(leftUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content("uryrury" + leftJson))
                .andExpect(status().isOk());
        this.mockMvc.perform(get(diff)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"UNEQUAL_SIZE\"}", true));
    }

    @Test
    public void testUnprocessableEntityScenario() throws Exception {
        String leftJson = TestUtils.getTestNode("left.txt");
        URI leftUri = URI.create("/v1/diff/43/left");
        this.mockMvc.perform(MockMvcRequestBuilders.post(leftUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftJson))
                .andExpect(status().isOk());
        URI diff = URI.create("/v1/diff/43");
        this.mockMvc.perform(get(diff)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

}
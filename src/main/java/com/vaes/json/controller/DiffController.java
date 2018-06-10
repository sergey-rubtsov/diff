package com.vaes.json.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@RequestMapping("/v1/diff/")
public interface DiffController {

    @ResponseBody
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    JsonNode getDiff(Integer id);

    @ResponseBody
    @RequestMapping(value = "/{id:.+}/left", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void postLeft(JsonNode left, Integer id);

    @ResponseBody
    @RequestMapping(value = "/{id:.+}/right", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void postRight(JsonNode right, Integer id);

}

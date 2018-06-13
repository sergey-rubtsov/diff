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
    @RequestMapping(value = "/{uid:.+}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    JsonNode getDiff(String uid);

    @ResponseBody
    @RequestMapping(value = "/{uid:.+}/left", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void postLeft(JsonNode left, String uid);

    @ResponseBody
    @RequestMapping(value = "/{uid:.+}/right", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void postRight(JsonNode right, String uid);

}

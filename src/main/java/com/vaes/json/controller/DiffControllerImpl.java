package com.vaes.json.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaes.json.domain.model.Type;
import com.vaes.json.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiffControllerImpl implements DiffController {

    @Autowired
    private DiffService diffService;

    public JsonNode getDiff(@PathVariable("uid") String uid) {
        return diffService.getDiff(uid);
    }

    public void postLeft(@RequestBody JsonNode left, @PathVariable("uid") String uid) {
        diffService.processJson(left, Type.LEFT, uid);
    }

    public void postRight(@RequestBody JsonNode right, @PathVariable("uid") String uid) {
        diffService.processJson(right, Type.RIGHT, uid);
    }
}

package com.vaes.json.controller;

import com.vaes.json.message.StatusMessage;
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
    StatusMessage getDiff(String uid);

    @ResponseBody
    @RequestMapping(value = "/{uid:.+}/left", method = RequestMethod.POST,
            produces = MediaType.ALL_VALUE)
    void postLeft(String left, String uid);

    @ResponseBody
    @RequestMapping(value = "/{uid:.+}/right", method = RequestMethod.POST,
            produces = MediaType.ALL_VALUE)
    void postRight(String right, String uid);

}

package com.waes.diff.controller;

import com.waes.diff.domain.model.Type;
import com.waes.diff.message.StatusMessage;
import com.waes.diff.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiffControllerImpl implements DiffController {

    @Autowired
    private DiffService diffService;

    public StatusMessage getDiff(@PathVariable("uid") String uid) {
        return diffService.getDiff(uid);
    }

    public void postLeft(@RequestBody String left, @PathVariable("uid") String uid) {
        diffService.storeData(left, Type.LEFT, uid);
    }

    public void postRight(@RequestBody String right, @PathVariable("uid") String uid) {
        diffService.storeData(right, Type.RIGHT, uid);
    }
}

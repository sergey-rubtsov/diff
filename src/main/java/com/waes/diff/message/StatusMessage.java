package com.waes.diff.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waes.diff.domain.model.Status;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusMessage {

    private Status status;

    private Integer size;

    private Integer offset;

    public StatusMessage status(Status status) {
        this.status = status;
        return this;
    }

    public StatusMessage size(Integer size) {
        this.size = size;
        return this;
    }

    public StatusMessage offset(Integer offset) {
        this.offset = offset;
        return this;
    }

}

package org.techfrog.fileprocessingservice.controller.errors;

import java.time.Instant;
import java.util.List;

public class ApiError {

    String status;
    List<String> errors;
    Instant timestamp;

    public ApiError(String status, List<String> errors) {
        this.status = status;
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    public String getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

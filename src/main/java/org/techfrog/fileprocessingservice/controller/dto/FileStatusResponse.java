package org.techfrog.fileprocessingservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStatusResponse {

    private String fileId;
    private String status;
    private Set<String> ipAddress;
}

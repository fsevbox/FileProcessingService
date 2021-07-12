package org.techfrog.fileprocessingservice.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSearchRequest {

    @NotBlank
    private String ipAddress;
    private String processStartDate;
}

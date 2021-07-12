package org.techfrog.fileprocessingservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileProcessRequest {

    @NotBlank(message = "content should not be null or empty")
    private String fileId;
}

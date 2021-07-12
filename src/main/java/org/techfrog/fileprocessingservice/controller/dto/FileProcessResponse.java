package org.techfrog.fileprocessingservice.controller.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileProcessResponse {

    private String fileId;
    private String status;
}

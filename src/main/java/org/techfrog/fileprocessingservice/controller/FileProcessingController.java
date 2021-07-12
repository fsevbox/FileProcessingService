package org.techfrog.fileprocessingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techfrog.fileprocessingservice.controller.dto.FileProcessRequest;
import org.techfrog.fileprocessingservice.controller.dto.FileProcessResponse;
import org.techfrog.fileprocessingservice.controller.dto.FileStatusResponse;
import org.techfrog.fileprocessingservice.controller.errors.ApiError;
import org.techfrog.fileprocessingservice.service.FileProcessingService;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping(value = "/process")
public class FileProcessingController {

    private FileProcessingService fileProcessingService;

    @Autowired
    public FileProcessingController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @Operation(summary = "Start process file by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process started successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FileProcessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public FileProcessResponse startFileProcess(@RequestBody FileProcessRequest request) {
        return fileProcessingService.startFileProcessing(request);
    }

    @Operation(summary = "Retrieve processing status by file id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process status found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FileStatusResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = "Process status not found", content = @Content)})
    @GetMapping(value = "/{fileId}/status", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FileStatusResponse> getFileProcessStatus(@PathVariable @NotBlank String fileId) {
        Optional<FileStatusResponse> status = fileProcessingService.getFileStatus(fileId);
        if (status.isPresent()) {
            return new ResponseEntity<>(status.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techfrog.fileprocessingservice.controller.dto.FileSearchRequest;
import org.techfrog.fileprocessingservice.controller.dto.FileSearchResponse;
import org.techfrog.fileprocessingservice.controller.errors.ApiError;
import org.techfrog.fileprocessingservice.service.FileSearchService;

import java.util.Optional;

@RestController
@RequestMapping("/search")
public class FileSearchController {

    private FileSearchService fileSearchService;

    @Autowired
    public FileSearchController(FileSearchService fileSearchService) {
        this.fileSearchService = fileSearchService;
    }

    @Operation(summary = "Search for files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FileSearchResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = "No files found", content = @Content)})
    @PostMapping(value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FileSearchResponse> searchFiles(@RequestBody FileSearchRequest request) {
        Optional<FileSearchResponse> searchResult = fileSearchService.searchForFiles(request);
        if (searchResult.isPresent()) {
            return new ResponseEntity<>(searchResult.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

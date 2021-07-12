package org.techfrog.fileprocessingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.techfrog.fileprocessingservice.controller.dto.FileSearchRequest;
import org.techfrog.fileprocessingservice.controller.dto.FileSearchResponse;
import org.techfrog.fileprocessingservice.repository.FileRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class FileSearchService {

    private FileRepository fileRepository;

    @Autowired
    public FileSearchService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Optional<FileSearchResponse> searchForFiles(FileSearchRequest request) {
        Set<String> result = fileRepository.findDocumentsForIP(request.getIpAddress());
        if (CollectionUtils.isEmpty(result)) {
            return Optional.empty();
        }
        return Optional.of(new FileSearchResponse(result));
    }
}

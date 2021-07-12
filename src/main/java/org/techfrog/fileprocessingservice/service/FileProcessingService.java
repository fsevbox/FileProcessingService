package org.techfrog.fileprocessingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.techfrog.fileprocessingservice.controller.dto.FileProcessRequest;
import org.techfrog.fileprocessingservice.controller.dto.FileProcessResponse;
import org.techfrog.fileprocessingservice.controller.dto.FileStatusResponse;
import org.techfrog.fileprocessingservice.domain.Task;
import org.techfrog.fileprocessingservice.repository.FileRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileProcessingService {

    private FileRepository fileRepository;
    private MessagePublisher messagePublisher;
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));

    @Autowired
    public FileProcessingService(FileRepository fileRepository, MessagePublisher messagePublisher) {
        this.fileRepository = fileRepository;
        this.messagePublisher = messagePublisher;
    }

    public FileProcessResponse startFileProcessing(FileProcessRequest request) {
        Task task = Task.builder()
                .taskId(UUID.randomUUID().toString())
                .fileId(request.getFileId())
                .creationDate(DATE_TIME_FORMATTER.format(Instant.now()))
                .status(Task.TaskStatus.PENDING)
                .build();

        fileRepository.createTask(task);
        messagePublisher.publishEvent(task.getFileId());

        return FileProcessResponse.builder()
                .fileId(task.getFileId())
                .status(task.getStatus().toString())
                .build();
    }

    public Optional<FileStatusResponse> getFileStatus(String fileId) {
        Optional<Task> optionalTask = fileRepository.findTaskByFileId(fileId);
        if (!optionalTask.isPresent()) {
            return Optional.empty();
        }
        Task task = optionalTask.get();
        FileStatusResponse response = FileStatusResponse.builder()
                .fileId(task.getFileId())
                .status(task.getStatus().name())
                .ipAddress(task.getResult() != null
                        ? task.getResult().getIps() : new HashSet<>())
                .build();
        return Optional.of(response);
    }
}

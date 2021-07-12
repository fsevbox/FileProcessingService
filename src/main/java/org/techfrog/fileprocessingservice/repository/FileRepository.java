package org.techfrog.fileprocessingservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.techfrog.fileprocessingservice.domain.Task;

import java.util.Optional;
import java.util.Set;

@Repository
public class FileRepository {

    private static final String HASH_FILES_KEYS = "files:";
    private static final String SORTED_FILES_BY_IP = "filesByIP:";

    private ValueOperations<String, Task> valueOperations;
    private ZSetOperations<String, String> sortedSetOperations;

    @Autowired
    public FileRepository(RedisTemplate redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
        this.sortedSetOperations = redisTemplate.opsForZSet();
    }

    public Optional<Task> findTaskByFileId(String id) {
        Task task = valueOperations.get(HASH_FILES_KEYS + id);
        return Optional.ofNullable(task);
    }

    public void createTask(Task task) {
        valueOperations.set(HASH_FILES_KEYS + task.getFileId(), task);
    }

    public Set<String> findDocumentsForIP(String ipAddress) {
        return sortedSetOperations.range(SORTED_FILES_BY_IP + ipAddress, 0, -1);
    }
}

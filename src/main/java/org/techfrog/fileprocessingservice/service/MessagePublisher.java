package org.techfrog.fileprocessingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private RedisTemplate<String, String> queueRedisTemplate;
    private ChannelTopic topic;

    @Autowired
    public MessagePublisher(RedisTemplate<String, String> queueRedisTemplate, ChannelTopic topic) {
        this.queueRedisTemplate = queueRedisTemplate;
        this.topic = topic;
    }

    public void publishEvent(final String message) {
        queueRedisTemplate.convertAndSend(topic.getTopic(), message);
    }
}

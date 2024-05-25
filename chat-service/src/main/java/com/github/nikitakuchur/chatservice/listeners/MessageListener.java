package com.github.nikitakuchur.chatservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nikitakuchur.chatservice.models.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "${chat-service.kafka.message-topic}", groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    public void listenChatMessages(String data) throws JsonProcessingException {
        Message message = objectMapper.readValue(data, Message.class);
        log.info("A new message has been received. Message: {}", message);
        messagingTemplate.convertAndSend("/topic/" + message.chatId(), data);
    }
}

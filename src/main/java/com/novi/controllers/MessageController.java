package com.novi.controllers;
import com.novi.dtos.MessageInputDTO;
import com.novi.dtos.MessageOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches/{matchId}/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // POST /matches/{matchId}/messages - Verstuur een bericht binnen een specifieke match
    @PostMapping
    public ResponseEntity<MessageOutputDTO> sendMessage(@PathVariable Long matchId, @RequestBody MessageInputDTO messageInputDTO) {
        MessageOutputDTO sentMessage = messageService.sendMessage(matchId, messageInputDTO);
        return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
    }

    // GET /matches/{matchId}/messages - Haal alle berichten op binnen een specifieke match
    @GetMapping
    public ResponseEntity<List<MessageOutputDTO>> getMessages(@PathVariable Long matchId) {
        List<MessageOutputDTO> messages = messageService.getMessagesByMatchId(matchId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // DELETE /messages/{messageId} - Verwijder een specifiek bericht
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

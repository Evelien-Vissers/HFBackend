package com.novi.HealForce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    // POST /matches/{matchId}/messages - Verstuur een bericht binnen een specifieke match
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Long matchId, @RequestBody MessageDTO messageDTO) {
        MessageDTO sentMessage = messageService.sendMessage(matchId, messageDTO);
        return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
    }
    // GET /matches/{matchId}/messages - Haal alle berichten op binnen een specifieke match
    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long matchId) {
        List<MessageDTO> messages = messageService.getMessagesByMatchId(matchId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    // DELETE /messages/{messageId} - Verwijder een specifiek bericht
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

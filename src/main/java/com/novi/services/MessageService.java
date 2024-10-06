package com.novi.services;

import com.novi.dtos.MessageInputDTO;
import com.novi.dtos.MessageOutputDTO;
import com.novi.entities.Message;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.MessageMapper;
import com.novi.repositories.MatchingRepository;
import com.novi.repositories.MessageRepository;
import com.novi.repositories.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MatchingRepository matchRepository;
    private final ProfileRepository profileRepository;

    public MessageService(MessageRepository messageRepository, MatchingRepository matchingRepository, ProfileRepository profileRepository) {
        this.messageRepository = messageRepository;
        this.matchRepository = matchingRepository;
        this.profileRepository = profileRepository;
    }

    // Verstuur een bericht binnen een specifieke match
    @Transactional
    public MessageOutputDTO sendMessage(Long matchId, MessageInputDTO messageInputDTO) {
        // Controleer of de match bestaat
        if (!matchRepository.existsById(matchId)) {
            throw new ResourceNotFoundException("Match with ID " + matchId + " not found");
        }

        // Maak een Message object aan met de informatie uit de DTO en de profielen
        Message message = MessageMapper.toMessage(messageInputDTO, matchId);
        message.setTimestamp(LocalDateTime.now()); // Stel de timestamp in op het huidige tijdstip
        message.setReadStatus(false); // Stel readStatus in op false (ongelezen bij verzending)
        message.setSenderProfile(senderProfile); // Koppel de verzender aan het bericht
        message.setReceiverProfile(receiverProfile); // Koppel de ontvanger aan het bericht

        // Sla het bericht op in de database
        Message savedMessage = messageRepository.save(message);

        // Zet het opgeslagen Message-object om naar een MessageOutputDTO via de MessageMapper
        return MessageMapper.toMessageOutputDTO(savedMessage);
    }

    // Haal alle berichten op binnen een specifieke match
    @Transactional(readOnly = true)
    public List<MessageOutputDTO> getMessagesByMatchId(Long matchId) {
        if (!matchRepository.existsById(matchId)) {
            throw new ResourceNotFoundException("Match with ID " + matchId + " not found");
        }
        List<Message> messages = messageRepository.findByMatchId(matchId);
        if (messages.isEmpty()) {
            throw new ResourceNotFoundException("Messages for match with ID " + matchId + " not found");
        }

        // Zet de berichten om naar MessageOutputDTO's via de MessageMapper
        return messages.stream().map(MessageMapper::toMessageOutputDTO).collect(Collectors.toList());
    }


    }



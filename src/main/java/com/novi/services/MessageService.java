package com.novi.services;

import com.novi.dtos.MessageInputDTO;
import com.novi.dtos.MessageOutputDTO;
import com.novi.entities.Matching;
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
    private final MatchingRepository matchingRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    public MessageService(MessageRepository messageRepository, MatchingRepository matchingRepository, ProfileRepository profileRepository, ProfileService profileService) {
        this.messageRepository = messageRepository;
        this.matchingRepository = matchingRepository;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }

    @Transactional
    public MessageOutputDTO sendMessage(Long matchID, MessageInputDTO messageInputDTO) {

        //Controleer of de match bestaat
        Matching matching = matchingRepository.findById(matchID)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID " + matchID + " not found"));

        //Haal de verzender en ontvanger profielen op uit de database
        Profile senderProfile = getCurrentUserProfile();
        Profile receiverProfile = profileRepository.findById(messageInputDTO.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver profile with ID " + messageInputDTO.getReceiverId() + " not found"));

        //Gebruik de MessageMapper om Message aan te maken
        Message message = MessageMapper.toMessage(messageInputDTO, senderProfile, receiverProfile, matching);

        //Sla het bericht op in de database
        Message savedMessage = messageRepository.save(message);

        //Converteer naar MessageOutputDTO en retourneer
        return MessageMapper.toMessageOutputDTO(savedMessage);
    }


    // Haal alle berichten op binnen een specifieke match
    @Transactional(readOnly = true)
    public List<MessageOutputDTO> getMessagesByMatchId(Long matchId) {
        //Controleer of de match bestaat
        if (!matchingRepository.existsById(matchId)) {
            throw new ResourceNotFoundException("Match with ID " + matchId + " not found");
        }
        //Haal alle berichten op
        List<Message> messages = messageRepository.findByMatching_MatchingId(matchId);

        // Zet de berichten om naar MessageOutputDTO's via de MessageMapper
        return messages.stream()
                .map(MessageMapper::toMessageOutputDTO)
                .collect(Collectors.toList());

    }



}



package com.novi.services;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.mappers.MatchingMapper;
import com.novi.mappers.ProfileMapper;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final String uploadDir = "src/main/resources/static/images";

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileMapper = new ProfileMapper();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void saveProfile(ProfileInputDTO profileInputDTO, MultipartFile profilePic) throws IOException {
        User currentUser = getCurrentUser();
        Profile profile = profileMapper.toEntity(profileInputDTO);
        profile.setUser(currentUser);
        currentUser.setProfile(profile);

        profile = profileRepository.save(profile);

        if (profilePic != null && !profilePic.isEmpty()) {
            String profilePicUrl = saveProfilePic(profilePic, profile.getId());
            profile.setProfilePicUrl(profilePicUrl);
            profileRepository.save(profile);
        }
    }


    public String saveProfilePic(MultipartFile profilePic, Long profileId) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileExtension = profilePic.getOriginalFilename().substring(profilePic.getOriginalFilename().lastIndexOf("."));
        String fileName = profileId + fileExtension;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(profilePic.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String profilePicUrl = "http://localhost:8080/images/" + fileName;
        System.out.println("Profile picture URL save as: " + profilePicUrl);
        return profilePicUrl;
    }

    public ProfileOutputDTO getUserProfileByProfileID(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::toProfileOutputDTO)
                .orElseGet(() -> {
                    User currentUser = getCurrentUser();
                    return profileRepository.findByUser(currentUser)
                            .map(profileMapper::toProfileOutputDTO)
                            .orElseThrow(() -> new RuntimeException("Profile not found"));
                });
    }


    public List<PotentialMatchesOutputDTO> findPotentialMatches() {
        User currentUser = getCurrentUser();
        Profile currentProfile = profileRepository.findByUser(currentUser)
                .orElseThrow(() -> new IllegalStateException("User has no profile. Please complete the questionnaire first."));

        String connectionPreference = currentProfile.getConnectionPreference();
        Long currentProfileId = currentProfile.getId();

        List<PotentialMatches> potentialMatches = profileRepository.findPotentialMatches(connectionPreference, currentProfileId);
        return MatchingMapper.toDTOList(potentialMatches);
    }


    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }

}
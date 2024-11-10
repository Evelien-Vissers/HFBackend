package com.novi.dtos;

public class CurrentMatchesOutputDTO {
    private String healforceName;
    private Long profileId;
    private String email;

    public CurrentMatchesOutputDTO(String healforceName, Long profileId, String email) {
        this.healforceName = healforceName;
        this.profileId = profileId;
        this.email = email;
    }

    public String getHealforceName() {
        return healforceName;
    }

    public void setHealforceName(String healforceName) {
        this.healforceName = healforceName;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


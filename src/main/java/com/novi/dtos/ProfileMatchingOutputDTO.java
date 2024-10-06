package com.novi.dtos;

public class ProfileMatchingOutputDTO {

        private String healforceName;
        private String healthChallenge;
        private String profilePic;
        private String location;

        // Constructor
        public ProfileMatchingOutputDTO() {}

        public ProfileMatchingOutputDTO(String healforceName, String healthChallenge, String profilePic, String location) {
            this.healforceName = healforceName;
            this.healthChallenge = healthChallenge;
            this.profilePic = profilePic;
            this.location = location;
        }

        // Getters and Setters
        public String getHealforceName() {
            return healforceName;
        }

        public void setHealforceName(String healforceName) {
            this.healforceName = healforceName;
        }

        public String getHealthChallenge() {
            return healthChallenge;
        }

        public void setHealthChallenge(String healthChallenge) {
            this.healthChallenge = healthChallenge;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

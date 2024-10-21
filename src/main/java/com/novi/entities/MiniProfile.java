package com.novi.entities;

public class MiniProfile {

        private String healforceName;
        private String healthChallenge;
        private String profilePic;
        private String city;
        private String country;

        //Constructors
        public MiniProfile() {
        }

        public MiniProfile(String healforceName, String healthChallenge, String profilePic, String city, String country) {
            this.healforceName = healforceName;
            this.healthChallenge = healthChallenge;
            this.profilePic = profilePic;
            this.city = city;
            this.country = country;
        }

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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
    }


-- Disable foreign key constraints
SET REFERENTIAL_INTEGRITY FALSE;

-- Insert roles
INSERT INTO roles (id, role_name, active, description, created_date, last_edited)
VALUES
    (1, 'ROLE_ADMIN', true, 'Administrator role with full access', '2024-10-01 09:00:00', '2024-10-01 09:00:00'),
    (2, 'ROLE_USER', true, 'Standard user role with limited access', '2024-10-01 09:00:00', '2024-10-01 09:00:00');

-- Insert users
INSERT INTO users (id, first_name, last_name, email, password, accepted_policies, registration_date, last_login, enabled, question, profile_user)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', '$2a$10$mS3PIxd001kO4Wh1BouV7e9TFA7tEUKWCwDLjZIyGOHpkqruJQe0a', true, '2023-01-01', '2024-10-05 10:15:30', true, 'What is your favorite color?', 1),
    (2, 'Jane', 'Smith', 'jane.smith@example.com', '$2a$10$8yKP03XhssehhWPkAnL7ceptPTy2awVq3MlgjotgPJomxD.PHrxlq', true, '2023-02-15', '2024-10-06 12:30:45', true, NULL, 2);

-- Insert profiles
INSERT INTO profiles (id, date_of_birth, city, country, gender, health_challenge, diagnosis_date, hospital, healing_choice, connection_preference, profile_pic_url, healforce_name, has_completed_questionnaire)
VALUES
    (1, '1988-04-15', 'Amsterdam', 'Netherlands', 'Male', 'Cancer', '2018-05', 'Amsterdam UMC', 'Conventional', 'Mix', 'http://localhost:8080/images/Male1.jpg', 'SkyWarrior88', true),
    (2, '1992-07-23', 'Berlin', 'Germany', 'Female', 'Heart Disease', '2017-10', 'Vivantes Klinikum', 'Alternative', 'Conventional', 'http://localhost:8080/images/Female1.jpg', 'HeartFighter92', true);

-- Insert matching
INSERT INTO matching (id, profile1, profile2, status_profile1, status_profile2, match_status, match_date)
VALUES
    (1, 1, 2, true, true, true, '2024-10-18T14:30:00');

-- Insert user roles
INSERT INTO user_roles (role_id, user_id)
VALUES
    (2, 1), -- Assign ROLE_USER to John Doe
    (2, 2); -- Assign ROLE_USER to Jane Smith

-- Enable foreign key constraints
SET REFERENTIAL_INTEGRITY TRUE;
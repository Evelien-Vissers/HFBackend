-- Disable foreign key constraints (H2 only)
-- This will only run in H2 since this syntax is specific to H2
-- Ensure you use the correct H2 syntax for comments
-- #IF H2
SET REFERENTIAL_INTEGRITY FALSE;
-- #ENDIF

-- Clean up tables before inserting test data
TRUNCATE TABLE user_roles;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE profiles;
TRUNCATE TABLE matching;

-- #IF H2
SET REFERENTIAL_INTEGRITY TRUE;
-- #ENDIF

-- Inserting test data for User
INSERT INTO Users (first_name, last_name, email, password, accepted_policies, registration_date, last_login, enabled, question)
VALUES
    ('Test', 'User1', 'testuser1@example.com', '$2a$10$mS3PIxd001kO4Wh1BouV7e9TFA7tEUKWCwDLjZIyGOHpkqruJQe0a', true, '2023-01-01', '2024-10-05 10:15:30', true, 'What is your favorite color?'),
    ('Test', 'User2', 'testuser2@example.com', '$2a$10$8yKP03XhssehhWPkAnL7ceptPTy2awVq3MlgjotgPJomxD.PHrxlq', true, '2023-02-15', '2024-10-06 12:30:45', true, NULL);

-- Inserting test data for Profile
INSERT INTO Profiles (date_of_birth, city, country, gender, health_challenge, diagnosis_date, hospital, healing_choice, connection_preference, profile_pic_url, healforce_name, has_completed_questionnaire)
VALUES
    ('1990-01-01', 'TestCity', 'TestCountry', 'Male', 'TestDisease', '2020-01', 'TestHospital', 'Conventional', 'Mix', 'http://localhost:8080/images/test1.jpg', 'TestHealer1', true),
    ('1991-01-01', 'TestCity2', 'TestCountry2', 'Female', 'TestDisease2', '2021-01', 'TestHospital2', 'Alternative', 'All Types', 'http://localhost:8080/images/test2.jpg', 'TestHealer2', true);

-- Inserting test data for Matching
INSERT INTO Matching (profile1, profile2, status_profile1, status_profile2, match_status, match_date)
VALUES
    (1, 2, true, true, true, '2024-10-18T14:30:00');

-- Inserting roles
INSERT INTO roles (role_name, active, description, created_date, last_edited)
VALUES
    ('ROLE_ADMIN', true, 'Administrator role with full access', '2024-10-01 09:00:00', '2024-10-01 09:00:00'),
    ('ROLE_USER', true, 'Standard user role with limited access', '2024-10-01 09:00:00', '2024-10-01 09:00:00');

-- Insert user roles conditionally to prevent duplicates
INSERT INTO public.user_roles(role_id, user_id)
SELECT 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_id = 1 AND user_id = 1);

INSERT INTO public.user_roles(role_id, user_id)
SELECT 2, 2
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_id = 2 AND user_id = 2);

-- Link users with profiles
UPDATE users SET profile_user = 1 WHERE email = 'testuser1@example.com';
UPDATE users SET profile_user = 2 WHERE email = 'testuser2@example.com';
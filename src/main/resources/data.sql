-- Inserting test data for User
INSERT INTO Users (first_name, last_name, email, password, accepted_policies, registration_date, last_login, enabled, question)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '$2a$10$mS3PIxd001kO4Wh1BouV7e9TFA7tEUKWCwDLjZIyGOHpkqruJQe0a', true,  '2023-01-01', '2024-10-05 10:15:30', true, 'What is your favorite color?'),
    ('Jane', 'Smith', 'jane.smith@example.com', '$2a$10$8yKP03XhssehhWPkAnL7ceptPTy2awVq3MlgjotgPJomxD.PHrxlq', true,  '2023-02-15', '2024-10-06 12:30:45', true, NULL),
    ('Emily', 'Johnson', 'emily.johnson@example.com', '$2a$10$ubMMfu3IJQiXqIuI.UUZROxmCX1RVbsXuFTG9TsIIaJeCLyJDTxZG', true,  '2023-03-20', '2024-10-07 09:10:20', true, 'What is the name of your first pet?'),
    ('Michael', 'Williams', 'michael.williams@example.com', '$2a$10$pvo3sFza2baTRZnml8n7RO2L1Z3MX9KYQYbOov8lws5nw.jpX4gJ2', true,  '2023-04-12', '2024-10-08 11:40:55', true, NULL),
    ('David', 'Brown', 'david.brown@example.com', '$2a$10$/w1Mb/dyGTMu31Ycp5ibtOxrKRlpx.B0uk33t732Td594v7gzHeGS', true,  '2023-05-18', '2024-10-09 08:25:10', true, 'What is your mother’s maiden name?'),
    ('Sarah', 'Davis', 'sarah.davis@example.com', '$2a$10$EBeaSAjHew4Nj0GQo6ZUbOLH.avY3TbMhidjngYxvu0PLyE6jJCPu', true, '2023-06-22', '2024-10-10 14:50:30', true, NULL),
    ('James', 'Miller', 'james.miller@example.com', '$2a$10$xXeOeTgctgW/mRQw/7OrLeY1PFPhMAR4PyRPSt9BelKkA5UZ2uvpS', true, '2023-07-14', '2024-10-11 13:05:15', true, 'What was your first car?'),
    ('Sophia', 'Wilson', 'sophia.wilson@example.com', '$2a$10$dqLXz/EqxnF6yT3wx5jbi.JCUPPZ2z5Pu924w12K.NF7L2t1c9EVi', true, '2023-08-01', '2024-10-12 15:20:45', true, 'What is your favorite movie?'),
    ('Max', 'Johnson', 'admin.johnson@example.com', '$2a$10$0Sk9Vv0R09cDM6MoXH81TecoVIW8RdNv6YnQjG.XeYjYGi.XwJseS', NULL, NULL, '2024-10-12 09:00:00', NULL, NULL),
    ('Bella', 'Davis', 'admin.davis@example.com', '$2a$10$YthMKU7hYvNwCCZN6JErGu2SthgDfGyfIUSfhlfWTQ/28vmEtLCii', NULL, NULL, '2024-10-11 16:30:45', NULL, NULL),
    ('Richard', 'Smith', 'admin.smith@example.com', '$2a$10$IuXDqN1RQhNFduPN1C0FQeJYRYgSBz/CyczoeEUQZ0qCqACabkLoS', NULL, NULL, '2024-10-10 14:15:30', NULL, NULL);

-- Inserting test data for Profile
INSERT INTO Profiles (date_of_birth, city, country, gender, health_challenge, diagnosis_date, hospital, healing_choice, connection_preference, profile_pic_url, healforce_name, has_completed_questionnaire)
VALUES
    ('1988-04-15', 'Amsterdam', 'Netherlands', 'Male', 'Cancer', '2018-05', 'Amsterdam UMC', 'Conventional', 'Mix', 'http://localhost:8080/images/Male1.jpg', 'SkyWarrior88', true),
    ('1992-07-23', 'Berlin', 'Germany', 'Female', 'Heart Disease', '2017-10', 'Vivantes Klinikum', 'Alternative', 'Conventional', 'http://localhost:8080/images/Female1.jpg', 'HeartFighter92', true),
    ('1985-11-11', 'New York', 'USA', 'Female', 'Diabetes', '2020-01', 'NYU Langone Health', 'Mix', 'Alternative', 'http://localhost:8080/images/Female2.jpg', 'LionSurvivor85', true),
    ('1995-02-02', 'Paris', 'France', 'Male', 'Asthma', '2019-03', 'Hôpital Saint-Louis', 'Conventional', 'All Types', 'http://localhost:8080/images/Male2.jpg', 'PhoenixHealer95', true),
    ('1983-05-18', 'Madrid', 'Spain', 'Male', 'Cancer', '2015-07', 'Hospital Clínico San Carlos', 'Alternative', 'Mix', 'http://localhost:8080/images/Male3.jpg', 'FireFighter83', true),
    ('1990-09-25', 'Sydney', 'Australia', 'Female', 'Heart Disease', '2021-06', 'Westmead Hospital', 'Mix', 'Conventional', 'http://localhost:8080/images/Female3.jpg', 'OceanWarrior90', true),
    ('1987-01-10', 'Tokyo', 'Japan', 'Male', 'Diabetes', '2016-12', 'Tokyo Medical University Hospital', 'Conventional', 'Alternative', 'http://localhost:8080/images/Male4.jpg', 'LotusSurvivor87', true),
    ('1993-08-05', 'Cape Town', 'South Africa', 'Female', 'Asthma', '2020-11', 'Tygerberg Hospital', 'Alternative', 'All Types', 'http://localhost:8080/images/Female4.jpg', 'MountainHealer93', true);

-- Inserting test data for Matching
INSERT INTO Matching (profile1, profile2, status_profile1, status_profile2, match_status, match_date)
VALUES
    (1, 2, true, true, true, '2024-10-18T14:30:00'),
    (3, 4, true, false, false, '2024-10-17T12:45:00'),
    (5, 6, false, false, false, '2024-10-16T09:15:00');

INSERT INTO roles (role_name, active, description, created_date, last_edited)
VALUES
    ('ROLE_ADMIN', true, 'Administrator role with full access', '2024-10-01 09:00:00', '2024-10-01 09:00:00'),
    ('ROLE_USER', true, 'Standard user role with limited access', '2024-10-01 09:00:00', '2024-10-01 09:00:00');

INSERT INTO public.user_roles(role_id, user_id)
VALUES
    (1, 11),
    (1, 10),
    (1, 9),
    (2, 8),
    (2, 7),
    (2, 6),
    (2, 5),
    (2, 4),
    (2, 3),
    (2, 2),
    (2, 1);

UPDATE users SET profile_user = 1 WHERE email = 'john.doe@example.com';
UPDATE users SET profile_user = 2 WHERE email = 'jane.smith@example.com';
UPDATE users SET profile_user = 3 WHERE email = 'emily.johnson@example.com';
UPDATE users SET profile_user = 4 WHERE email = 'michael.williams@example.com';
UPDATE users SET profile_user = 5 WHERE email = 'david.brown@example.com';
UPDATE users SET profile_user = 6 WHERE email = 'sarah.davis@example.com';
UPDATE users SET profile_user = 7 WHERE email = 'james.miller@example.com';
UPDATE users SET profile_user = 8 WHERE email = 'sophia.wilson@example.com';
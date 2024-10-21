-- Inserting test data for User
INSERT INTO User (first_name, last_name, email, password, accepted_privacystatementuseragreement, registration_date, last_login, has_completedquestionnaire, question)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'password123', true, '2023-01-01', '2024-10-05 10:15:30', true, 'What is your favorite color?'),
    ('Jane', 'Smith', 'jane.smith@example.com', 'password456', true, '2023-02-15', '2024-10-06 12:30:45', false, NULL),
    ('Emily', 'Johnson', 'emily.johnson@example.com', 'password789', true, '2023-03-20', '2024-10-07 09:10:20', true, 'What is the name of your first pet?'),
    ('Michael', 'Williams', 'michael.williams@example.com', 'password012', true, '2023-04-12', '2024-10-08 11:40:55', false, NULL),
    ('David', 'Brown', 'david.brown@example.com', 'password345', true, '2023-05-18', '2024-10-09 08:25:10', true, 'What is your mother’s maiden name?'),
    ('Sarah', 'Davis', 'sarah.davis@example.com', 'password678', true, '2023-06-22', '2024-10-10 14:50:30', false, NULL),
    ('James', 'Miller', 'james.miller@example.com', 'password901', true, '2023-07-14', '2024-10-11 13:05:15', true, 'What was your first car?'),
    ('Sophia', 'Wilson', 'sophia.wilson@example.com', 'password234', true, '2023-08-01', '2024-10-12 15:20:45', true, 'What is your favorite movie?');

-- Inserting test data for Profile
INSERT INTO Profile (profile_id, date_of_birth, city, country, gender, health_challenge, diagnosis_date, healing_choice, connection_preference, profile_pic, healforce_name)
VALUES
    ('8a6e1d20-5d44-4ea8-8d5b-019b431c9c3f', '1988-04-15', 'Amsterdam', 'Netherlands', 'Male', 'Cancer', '2018-05', 'Conventional', 'Mix', 'profilepic1.png', 'SkyWarrior88'),
    ('7e1b5f42-742f-4733-9377-7a8d736091a2', '1992-07-23', 'Berlin', 'Germany', 'Female', 'Heart Disease', '2017-10', 'Alternative', 'Conventional', 'profilepic2.png', 'HeartFighter92'),
    ('d6f6c5eb-8c65-4201-a779-16ed4366f5fc', '1985-11-11', 'New York', 'USA', 'Male', 'Diabetes', '2020-01', 'Mix', 'Alternative', 'profilepic3.png', 'LionSurvivor85'),
    ('cf81f2d2-224f-46be-935d-4a91d6f0e83c', '1995-02-02', 'Paris', 'France', 'Female', 'Asthma', '2019-03', 'Conventional', 'All Types', 'profilepic4.png', 'PhoenixHealer95'),
    ('543d0b2b-8352-4687-b86b-59a261530e45', '1983-05-18', 'Madrid', 'Spain', 'Female', 'Cancer', '2015-07', 'Alternative', 'Mix', 'profilepic5.png', 'FireFighter83'),
    ('f4a6892f-4fc4-44bb-9cc6-32f70d7f12f3', '1990-09-25', 'Sydney', 'Australia', 'Male', 'Heart Disease', '2021-06', 'Mix', 'Conventional', 'profilepic6.png', 'OceanWarrior90'),
    ('267dbe3f-704b-49f7-b162-9b04731d5f67', '1987-01-10', 'Tokyo', 'Japan', 'Female', 'Diabetes', '2016-12', 'Conventional', 'Alternative', 'profilepic7.png', 'LotusSurvivor87'),
    ('5c1d75a1-891e-4656-8618-d271c3e582ad', '1993-08-05', 'Cape Town', 'South Africa', 'Male', 'Asthma', '2020-11', 'Alternative', 'All Types', 'profilepic8.png', 'MountainHealer93');

-- Inserting test data for Matching
INSERT INTO Matching (matching_id, profile1, profile2, statusProfile1, statusProfile2, matchStatus, matchDate)
VALUES
    ('1f4a42a6-47b4-4a4d-baea-1d4d92f24f61', '8a6e1d20-5d44-4ea8-8d5b-019b431c9c3f', '7e1b5f42-742f-4733-9377-7a8d736091a2', true, true, true, '2024-10-18T14:30:00'),
    ('9d3b5f88-10a1-48da-b4f7-e6b8f7bc0f28', 'd6f6c5eb-8c65-4201-a779-16ed4366f5fc', 'cf81f2d2-224f-46be-935d-4a91d6f0e83c', true, false, false, '2024-10-17T12:45:00'),
    ('5a18f4b7-6ac4-41cb-8611-f43a52a8e7d7', '543d0b2b-8352-4687-b86b-59a261530e45', 'f4a6892f-4fc4-44bb-9cc6-32f70d7f12f3', false, false, false, '2024-10-16T09:15:00');

-- Inserting test data for Admin
INSERT INTO Admin (email, password, lastEdited)
VALUES
    ('admin.johnson@example.com', 'adminpass123', '2024-10-12 09:00:00'),
    ('admin.davis@example.com', 'adminpass456', '2024-10-11 16:30:45'),
    ('admin.smith@example.com', 'adminpass789', '2024-10-10 14:15:30');
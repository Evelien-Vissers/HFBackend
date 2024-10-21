-- Inserting test data for User
INSERT INTO Users (first_name, last_name, email, password, accepted_policies, role, registration_date, last_login, has_completed_questionnaire, enabled, question)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'password123', true, user, '2023-01-01', '2024-10-05 10:15:30', true, true, 'What is your favorite color?'),
    ('Jane', 'Smith', 'jane.smith@example.com', 'password456', true, user, '2023-02-15', '2024-10-06 12:30:45', false, true, NULL),
    ('Emily', 'Johnson', 'emily.johnson@example.com', 'password789', true, user, '2023-03-20', '2024-10-07 09:10:20', true, true, 'What is the name of your first pet?'),
    ('Michael', 'Williams', 'michael.williams@example.com', 'password012', true, user, '2023-04-12', '2024-10-08 11:40:55', false, true, NULL),
    ('David', 'Brown', 'david.brown@example.com', 'password345', true, user, '2023-05-18', '2024-10-09 08:25:10', true, true, 'What is your mother’s maiden name?'),
    ('Sarah', 'Davis', 'sarah.davis@example.com', 'password678', true, user, '2023-06-22', '2024-10-10 14:50:30', false, true, NULL),
    ('James', 'Miller', 'james.miller@example.com', 'password901', true, user, '2023-07-14', '2024-10-11 13:05:15', true, true, 'What was your first car?'),
    ('Sophia', 'Wilson', 'sophia.wilson@example.com', 'password234', true, user, '2023-08-01', '2024-10-12 15:20:45', true, true, 'What is your favorite movie?');

-- Inserting test data for Profile
INSERT INTO Profiles (date_of_birth, city, country, gender, health_challenge, diagnosis_date, healing_choice, connection_preference, profile_pic, healforce_name)
VALUES
    ('1988-04-15', 'Amsterdam', 'Netherlands', 'Male', 'Cancer', '2018-05', 'Conventional', 'Mix', 'profilepic1.png', 'SkyWarrior88'),
    ('1992-07-23', 'Berlin', 'Germany', 'Female', 'Heart Disease', '2017-10', 'Alternative', 'Conventional', 'profilepic2.png', 'HeartFighter92'),
    ('1985-11-11', 'New York', 'USA', 'Male', 'Diabetes', '2020-01', 'Mix', 'Alternative', 'profilepic3.png', 'LionSurvivor85'),
    ('1995-02-02', 'Paris', 'France', 'Female', 'Asthma', '2019-03', 'Conventional', 'All Types', 'profilepic4.png', 'PhoenixHealer95'),
    ('1983-05-18', 'Madrid', 'Spain', 'Female', 'Cancer', '2015-07', 'Alternative', 'Mix', 'profilepic5.png', 'FireFighter83'),
    ('1990-09-25', 'Sydney', 'Australia', 'Male', 'Heart Disease', '2021-06', 'Mix', 'Conventional', 'profilepic6.png', 'OceanWarrior90'),
    ('1987-01-10', 'Tokyo', 'Japan', 'Female', 'Diabetes', '2016-12', 'Conventional', 'Alternative', 'profilepic7.png', 'LotusSurvivor87'),
    ('1993-08-05', 'Cape Town', 'South Africa', 'Male', 'Asthma', '2020-11', 'Alternative', 'All Types', 'profilepic8.png', 'MountainHealer93');

-- Inserting test data for Matching
INSERT INTO Matching (profile1, profile2, status_profile1, status_profile2, match_status, match_date)
VALUES
    (1, 2, true, true, true, '2024-10-18T14:30:00'),
    (3, 4, true, false, false, '2024-10-17T12:45:00'),
    (5, 6, false, false, false, '2024-10-16T09:15:00');

-- Inserting test data for Admin
INSERT INTO Admins (email, password, last_login)
VALUES
    ('admin.johnson@example.com', 'adminpass123', '2024-10-12 09:00:00'),
    ('admin.davis@example.com', 'adminpass456', '2024-10-11 16:30:45'),
    ('admin.smith@example.com', 'adminpass789', '2024-10-10 14:15:30');

INSERT INTO roles (role_name, active, description, created_date, last_edited)
VALUES
    ('ADMIN', true, 'Administrator role with full access', '2024-10-01 09:00:00', '2024-10-01 09:00:00'),
    ('USER', true, 'Standard user role with limited access', '2024-10-01 09:00:00', '2024-10-01 09:00:00');
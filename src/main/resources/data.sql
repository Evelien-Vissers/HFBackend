-- BaseEntity fields are inherited, no direct table for BaseEntity.

-- Inserting test data for User
INSERT INTO User (id, first_name, last_name, email, password, role, accepted_privacyStatementUserAgreement, verifiedEmail, registrationDate, lastLogin, hasCompletedQuestionnaire, profile_id)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', 'password123', 'User', true, true, '2023-01-10', '2024-10-05T10:15:30', true, 1),
    (2, 'Jane', 'Smith', 'jane.smith@example.com', 'password456', 'Admin', true, true, '2023-03-15', '2024-10-05T11:20:45', false, 2);
    (3, 'Alice', 'Johnson', 'alice.johnson@example.com', 'password789', 'User', true, true, '2023-05-20', '2024-10-05T12:45:10', true, 3),
    (4, 'Bob', 'Brown', 'bob.brown@example.com', 'password987', 'User', true, false, '2023-07-22', '2024-10-05T14:05:20', true, 4);

-- Inserting test data for Profile
INSERT INTO Profile (id, dateOfBirth, location, gender, healthChallenge, diagnosisDate, healingChoice, connectionPreference, profilePic, healforceName, profileID, createdDate, lastEdited)
VALUES
    (1, '1985-05-23', 'Amsterdam', 'Male', 'Cancer', '2020-06', 'Holistic', 'all types', 'profile1.jpg', 'HealerJohn', 101, '2024-10-01T10:00:00', '2024-10-01T10:30:00'),
    (2, '1990-11-12', 'Rotterdam', 'Female', 'Breast Cancer', '2021-09', 'Medical', 'similar experience', 'profile2.jpg', 'FighterJane', 102, '2024-10-02T14:00:00', '2024-10-02T14:30:00');
    (3, '1978-03-30', 'Utrecht', 'Female', 'Leukemia', '2019-03', 'Holistic', 'all types', 'profile3.jpg', 'WarriorAlice', 103, '2024-10-03T15:00:00', '2024-10-03T15:30:00'),
    (4, '1982-08-16', 'Eindhoven', 'Male', 'Lung Cancer', '2018-11', 'Medical', 'similar experience', 'profile4.jpg', 'SurvivorBob', 104, '2024-10-04T16:00:00', '2024-10-04T16:30:00');

-- Inserting test data for Matching
INSERT INTO Matching (matchingId, statusProfile1, statusProfile2, matchDate, createdDate, lastEdited)
VALUES
    (1, true, true, '2024-10-03T12:00:00', '2024-10-03T12:00:00', '2024-10-03T12:30:00'),
    (2, true, false, '2024-10-04T15:00:00', '2024-10-04T15:00:00', '2024-10-04T15:30:00');
    (3, true, true, '2024-10-05T17:00:00', '2024-10-05T17:00:00', '2024-10-05T17:30:00'),
    (4, false, true, '2024-10-06T18:00:00', '2024-10-06T18:00:00', '2024-10-06T18:30:00');

-- Inserting test data for Message
INSERT INTO Message (messageId, content, timestamp, senderId, receiverId, readStatus, createdDate, lastEdited)
VALUES
    (1, 'Hello, how are you?', '2024-10-05T09:15:30', 1, 2, false, '2024-10-05T09:15:30', '2024-10-05T09:15:30'),
    (2, 'I am doing great, thank you!', '2024-10-05T09:20:30', 2, 1, true, '2024-10-05T09:20:30', '2024-10-05T09:20:30');
    (3, 'What are your treatment options?', '2024-10-05T10:30:15', 3, 4, false, '2024-10-05T10:30:15', '2024-10-05T10:30:15'),
    (4, 'I am considering holistic therapy.', '2024-10-05T10:45:20', 4, 3, true, '2024-10-05T10:45:20', '2024-10-05T10:45:20');


-- Inserting test data for Admin
INSERT INTO Admin (id, email, password, createdDate, lastEdited)
VALUES
    (1, 'admin@example.com', 'adminPass789', '2024-10-01T08:00:00', '2024-10-01T08:30:00');
    (2, 'superadmin@example.com', 'superAdminPass123', '2024-10-02T09:00:00', '2024-10-02T09:30:00'),
    (3, 'support@example.com', 'supportPass456', '2024-10-03T10:00:00', '2024-10-03T10:30:00'),
    (4, 'admin2@example.com', 'admin2Pass789', '2024-10-04T11:00:00', '2024-10-04T11:30:00');
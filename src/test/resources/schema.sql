-- Create the roles table
CREATE TABLE IF NOT EXISTS roles (
                                     id SERIAL PRIMARY KEY,
                                     role_name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    description VARCHAR(255),
    created_date TIMESTAMP,
    last_edited TIMESTAMP
    );

-- Create the profiles table
CREATE TABLE IF NOT EXISTS profiles (
                                        id BIGSERIAL PRIMARY KEY,
                                        date_of_birth DATE NOT NULL,
                                        city VARCHAR(100),
    country VARCHAR(100),
    gender VARCHAR(50),
    health_challenge VARCHAR(255),
    diagnosis_date VARCHAR(50),
    hospital VARCHAR(255),
    healing_choice VARCHAR(50),
    connection_preference VARCHAR(50),
    profile_pic_url VARCHAR(255),
    healforce_name VARCHAR(100),
    has_completed_questionnaire BOOLEAN
    );

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    accepted_policies BOOLEAN,
    registration_date DATE,
    last_login TIMESTAMP,
    enabled BOOLEAN,
    question VARCHAR(255),
    profile_user BIGINT,
    CONSTRAINT fk_profile_user FOREIGN KEY (profile_user) REFERENCES profiles(id) ON DELETE SET NULL
    );

-- Create the matching table
CREATE TABLE IF NOT EXISTS matching (
                                        id BIGSERIAL PRIMARY KEY,
                                        profile1 BIGINT NOT NULL,
                                        profile2 BIGINT NOT NULL,
                                        status_profile1 BOOLEAN,
                                        status_profile2 BOOLEAN,
                                        match_status BOOLEAN,
                                        match_date TIMESTAMP,
                                        CONSTRAINT fk_profile1 FOREIGN KEY (profile1) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_profile2 FOREIGN KEY (profile2) REFERENCES profiles(id) ON DELETE CASCADE
    );

-- Create the user_roles table
CREATE TABLE IF NOT EXISTS user_roles (
                                          role_id BIGINT NOT NULL,
                                          user_id BIGINT NOT NULL,
                                          CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
    );
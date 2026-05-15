-- Drop tables in reverse order to respect foreign key constraints
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
-- Company
CREATE TABLE company
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(255)        NOT NULL,
    company_logo VARCHAR(255),
    slug         VARCHAR(255) UNIQUE NOT NULL
);

-- Company
CREATE TABLE users
(
    id            UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    company_id    UUID         NOT NULL REFERENCES company (id) ON DELETE CASCADE,
    username      VARCHAR(255) NOT NULL ,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      TEXT NOT NULL,
    profile_image VARCHAR(255),
    is_active     BOOLEAN      NOT NULL DEFAULT TRUE,
    UNIQUE(company_id, username)
);

-- Session
CREATE TABLE session
(
    id         UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    user_id    UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    token      TEXT NOT NULL UNIQUE,
    ip_address VARCHAR(45),
    user_agent VARCHAR(512),
    expires_at TIMESTAMPTZ    NOT NULL,
    created_at TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);

-- Role
CREATE TABLE role
(
    id          UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(512) ,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Action
CREATE TABLE action
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(512)
);

-- Company Role
CREATE TABLE user_role
(
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Action Role
CREATE TABLE action_role
(
    role_id   UUID NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    action_id UUID NOT NULL REFERENCES action (id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, action_id)
);

-- Customer
CREATE TABLE customer
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID NOT NULL REFERENCES company (id) ON DELETE CASCADE,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    phone      VARCHAR(255)NOT NULL,
    UNIQUE(company_id, email),
    UNIQUE(company_id, phone)
);

-- Booking
CREATE TABLE booking
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id  UUID        NOT NULL REFERENCES company (id) ON DELETE CASCADE,
    customer_id UUID        NOT NULL REFERENCES customer (id) ON DELETE CASCADE,
    booking_number VARCHAR(255) NOT NULL,
    date_from   TIMESTAMPTZ NOT NULL,
    date_to     TIMESTAMPTZ NOT NULL,
    UNIQUE(company_id, booking_number),
    CHECK (date_to > date_from)
);

-- Indexes
CREATE INDEX idx_user_email ON users (email);
CREATE INDEX idx_user_company ON users (company_id);
CREATE INDEX idx_booking_company on booking (company_id);
CREATE INDEX idx_booking_customer on booking (customer_id);
CREATE INDEX idx_booking_company_date ON booking(company_id, date_from);

CREATE INDEX idx_session_user ON session (user_id);
CREATE INDEX idx_session_token ON session (token);
CREATE INDEX idx_session_expires ON session (expires_at);

CREATE INDEX idx_company_slug ON company (slug);

WITH inserted_company AS (
    INSERT INTO company (name, company_logo, slug)
        VALUES ('Isaac Sanz S.L', '', 'isg')
        RETURNING id)

INSERT
INTO users (company_id, username, email, password, profile_image)
SELECT id,
       'isanz',
       'isaacsanz@proton.me',
       '$2a$12$Z.4XMvTEvbqO05dK6v3Ttu3qY/ryyNob4GezVeiqApXPKK/E7gC5O',
       ''
FROM inserted_company;


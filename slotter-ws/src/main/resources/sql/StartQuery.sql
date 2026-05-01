-- Drop tables in reverse order to respect foreign key constraints
DROP TABLE IF EXISTS audit_log CASCADE;
DROP TABLE IF EXISTS company_theme CASCADE;
DROP TABLE IF EXISTS action_role CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS action CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS session CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS company CASCADE;

-- Company
CREATE TABLE company
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(255) NOT NULL,
    company_logo VARCHAR(255),
    slug         VARCHAR(255) UNIQUE NOT NULL
);

-- Company
CREATE TABLE users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id    UUID NOT NULL REFERENCES company (id),
    username      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    is_active     BOOLEAN NOT NULL DEFAULT TRUE
);

-- Session
CREATE TABLE session
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID NOT NULL REFERENCES users (id),
    token      VARCHAR(512) NOT NULL UNIQUE,
    ip_address VARCHAR(45),
    user_agent VARCHAR(512),
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Role
CREATE TABLE role
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(512),
    is_active   BOOLEAN NOT NULL DEFAULT TRUE
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
    id_user UUID NOT NULL REFERENCES users (id),
    id_role UUID NOT NULL REFERENCES role (id),
    PRIMARY KEY (id_user, id_role)
);

-- Action Role
CREATE TABLE action_role
(
    id_role   UUID NOT NULL REFERENCES role (id),
    id_action UUID NOT NULL REFERENCES action (id),
    PRIMARY KEY (id_role, id_action)
);

-- Company Theme
CREATE TABLE company_theme
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_company UUID NOT NULL REFERENCES company (id),
    key        VARCHAR(50) NOT NULL,
    value      VARCHAR(50) NOT NULL
);

-- Audit Log
CREATE TABLE audit_log
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID REFERENCES users (id),
    table_name VARCHAR(255) NOT NULL,
    record_id  UUID NOT NULL,
    action     VARCHAR(10) NOT NULL CHECK (action IN ('CREATE', 'UPDATE', 'DELETE')),
    old_values JSONB,
    new_values JSONB,
    ip_address VARCHAR(45),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Indexes
CREATE INDEX idx_user_email ON users (email);
CREATE INDEX idx_user_company ON users (company_id);

CREATE INDEX idx_session_user ON session (user_id);
CREATE INDEX idx_session_token ON session (token);
CREATE INDEX idx_session_expires ON session (expires_at);

CREATE INDEX idx_audit_log_user ON audit_log (user_id);
CREATE INDEX idx_audit_log_table ON audit_log (table_name, record_id);

CREATE INDEX idx_company_slug ON company (slug);
CREATE INDEX idx_company_theme ON company_theme (id_company);
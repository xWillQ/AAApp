CREATE UNIQUE INDEX idx_users_login ON users (login);
CREATE INDEX idx_authorities_login ON authorities (userId);
CREATE UNIQUE INDEX idx_users_login ON users (login);
CREATE INDEX idx_permissions_login ON permissions (login);
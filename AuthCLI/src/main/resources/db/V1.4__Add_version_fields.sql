ALTER TABLE users ADD COLUMN version int DEFAULT 1;
ALTER TABLE authorities ADD COLUMN version int DEFAULT 1;
ALTER TABLE activities ADD COLUMN version int DEFAULT 1;
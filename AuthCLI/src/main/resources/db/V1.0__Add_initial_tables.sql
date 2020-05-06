CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(10),
    hash VARCHAR(64),
    salt VARCHAR(32)
);

CREATE TABLE authorities(
    id INT PRIMARY KEY AUTO_INCREMENT,
    res VARCHAR(255),
    role VARCHAR(7),
    userId INT REFERENCES users (id)
);

CREATE TABLE activities(
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT REFERENCES users (id),
    res VARCHAR(255),
    role VARCHAR(7),
    ds VARCHAR(10),
    de VARCHAR(10),
    vol INT
);

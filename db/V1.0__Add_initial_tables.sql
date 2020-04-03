CREATE TABLE users(
login VARCHAR(10) PRIMARY KEY,
hash VARCHAR(64),
salt VARCHAR(32)
);

CREATE TABLE permissions(
id INT PRIMARY KEY AUTO_INCREMENT,
res VARCHAR(255),
role VARCHAR(7),
login VARCHAR(10)  REFERENCES users (login)
);

CREATE TABLE activities(
id INT PRIMARY KEY AUTO_INCREMENT,
login VARCHAR(10) REFERENCES users (login),
res VARCHAR(255),
role VARCHAR(7),
ds VARCHAR(10),
de VARCHAR(10),
vol INT
);

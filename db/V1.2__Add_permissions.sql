INSERT INTO permissions(res, role, login) VALUES ('A', 'READ', 'vasya');
INSERT INTO permissions(res, role, login) VALUES ('A.B.C', 'WRITE', 'vasya');
INSERT INTO permissions(res, role, login) VALUES ('A.B', 'EXECUTE', 'admin');
INSERT INTO permissions(res, role, login) VALUES ('A', 'READ', 'admin');
INSERT INTO permissions(res, role, login) VALUES ('A.B', 'WRITE', 'admin');
INSERT INTO permissions(res, role, login) VALUES ('A.B.C', 'READ', 'admin');
INSERT INTO permissions(res, role, login) VALUES ('B', 'EXECUTE', 'q');
INSERT INTO permissions(res, role, login) VALUES ('A.A.A', 'EXECUTE', 'vasya');

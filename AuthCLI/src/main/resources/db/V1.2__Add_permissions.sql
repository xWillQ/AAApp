INSERT INTO permissions(res, role, login)
VALUES
    ('A', 'READ', 'vasya'),
    ('A.B.C', 'WRITE', 'vasya'),
    ('A.B', 'EXECUTE', 'admin'),
    ('A', 'READ', 'admin'),
    ('A.B', 'WRITE', 'admin'),
    ('A.B.C', 'READ', 'admin'),
    ('B', 'EXECUTE', 'q'),
    ('A.A.A', 'EXECUTE', 'vasya');

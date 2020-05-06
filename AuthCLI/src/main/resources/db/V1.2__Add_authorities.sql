INSERT INTO authorities(res, role, userId)
VALUES
    ('A', 'READ', 1),
    ('A.B.C', 'WRITE', 1),
    ('A.B', 'EXECUTE', 2),
    ('A', 'READ', 2),
    ('A.B', 'WRITE', 2),
    ('A.B.C', 'READ', 2),
    ('B', 'EXECUTE', 3),
    ('A.A.A', 'EXECUTE', 1);

INSERT INTO authorities
VALUES
    (1, 'A', 'READ', 1),
    (2, 'A.B.C', 'WRITE', 1),
    (3, 'A.B', 'EXECUTE', 2),
    (4, 'A', 'READ', 2),
    (5, 'A.B', 'WRITE', 2),
    (6, 'A.B.C', 'READ', 2),
    (7, 'B', 'EXECUTE', 3),
    (8, 'A.A.A', 'EXECUTE', 1);

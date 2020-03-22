package com.kafedra.bd.domain

import com.kafedra.bd.Role

val permissions = listOf(Permission("A", Role.READ, users[0]),
        Permission("A.B.C", Role.WRITE, users[0]), Permission("A.B", Role.EXECUTE, users[1]),
        Permission("A", Role.READ, users[1]), Permission("A.B", Role.WRITE, users[1]),
        Permission("A.B.C", Role.READ, users[1]), Permission("B", Role.EXECUTE, users[2]),
        Permission("A.A.A", Role.EXECUTE, users[0]))
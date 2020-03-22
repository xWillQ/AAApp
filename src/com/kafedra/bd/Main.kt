package com.kafedra.bd

import com.kafedra.bd.domain.Activity
import com.kafedra.bd.domain.Permission
import com.kafedra.bd.domain.User
import kotlin.system.exitProcess


private val users = listOf(User("vasya", "iYqHUi2<2zPhrGIL8]?p8m;bteA?ETaT",
        "dc6a8709e9fc8de1acea34fdc98c842911686ca0c2a0b12127c512a5ed7ab382"),
        User("admin", "olMMIDct3GkrY:?Xp1WDJOPTw2IY0`a[",
                "c6d6ced902fe90f039f168837f7ce3d313df040e071281317fc6781a60cac2bc"),
        User("q", "TtVaKT?vkBlIrtbChI72yef7iWkLxkw4",
                "50bba7d209a17a3c36a3df151276d233ca868bf3b518165a6510b8e8c0bc2b7a"),
        User("abcdefghij", "nUMXGQvmro8b5;AX7dLpwS_A4L`;RH^_",
                "2c06d373cd2549c31d8c1758daaa7773a8b905e32eb430f566c4c391827db121"))

private val permissions = listOf(Permission("A", Role.READ, users[0]),
        Permission("A.B.C", Role.WRITE, users[0]), Permission("A.B", Role.EXECUTE, users[1]),
        Permission("A", Role.READ, users[1]), Permission("A.B", Role.WRITE, users[1]),
        Permission("A.B.C", Role.READ, users[1]), Permission("B", Role.EXECUTE, users[2]),
        Permission("A.A.A", Role.EXECUTE, users[0]))

private val activities = mutableListOf<Activity>()

fun main(args: Array<String>) {
    val app = App(users, permissions, activities)
    exitProcess(app.run(args).code)
}
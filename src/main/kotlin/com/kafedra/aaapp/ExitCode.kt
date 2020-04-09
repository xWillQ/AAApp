package com.kafedra.aaapp

enum class ExitCode(val code: Int) {
    SUCCESS(0),
    HELP(1),
    INVALID_LOGIN(2),
    UNKNOWN_LOGIN(3),
    WRONG_PASS(4),
    UNKNOWN_ROLE(5),
    NO_ACCESS(6),
    INVALID_ACTIVITY(7)
}
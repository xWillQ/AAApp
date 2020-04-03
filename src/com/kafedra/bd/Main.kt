package com.kafedra.bd

import com.kafedra.bd.domain.Activity
import com.kafedra.bd.domain.Permission
import com.kafedra.bd.domain.User
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val app = App()
    exitProcess(app.run(args).code)
}
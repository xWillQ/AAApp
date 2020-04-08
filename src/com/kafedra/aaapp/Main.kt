package com.kafedra.aaapp

import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val app = App()
    exitProcess(app.run(args).code)
}
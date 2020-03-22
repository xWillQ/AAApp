package com.kafedra.bd

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    exitProcess(App.run(args).code)
}
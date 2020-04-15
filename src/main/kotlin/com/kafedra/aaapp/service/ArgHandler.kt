package com.kafedra.aaapp.service

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import org.apache.logging.log4j.LogManager

class ArgHandler(args: Array<String>) {
    private val parser = ArgParser("BD")
    private val empty = args.isEmpty()

    val login by parser.option(ArgType.String, shortName = "login", description = "Login")
    val pass by parser.option(ArgType.String, shortName = "pass", description = "Password")
    val role by parser.option(ArgType.String, shortName = "role", description = "Role")
    val res by parser.option(ArgType.String, shortName = "res", description = "Resource")
    val ds by parser.option(ArgType.String, shortName = "ds", description = "Start date")
    val de by parser.option(ArgType.String, shortName = "de", description = "End date")
    val vol by parser.option(ArgType.String, shortName = "vol", description = "Volume")
    var help = false

    init {
        try {
            LogManager.getLogger().info("Parsing Arguments")
            parser.parse(args)
        } catch (e: IllegalStateException) {
            LogManager.getLogger().error("Unknown argument")
            help = true
        }
    }

    private val authentication = login != null && pass != null
    private val authorization = res != null && role != null
    private val accounting = ds != null && de != null && vol != null

    fun isArgs() = !empty

    fun needAuthentication() = authentication

    fun needAuthorization() = authorization

    fun needAccounting() = accounting
}

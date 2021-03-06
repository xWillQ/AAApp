package com.kafedra.aaapp

import com.google.inject.Guice
import com.google.inject.Injector
import com.kafedra.aaapp.ExitCode.*
import com.kafedra.aaapp.service.Accounting
import com.kafedra.aaapp.service.ArgHandler
import com.kafedra.aaapp.service.Authentication
import com.kafedra.aaapp.service.Authorization
import org.apache.logging.log4j.LogManager

class App {
    private val logger = LogManager.getLogger()
    private fun printHelp() = println(
            "Usage: app.jar [-h] [-login <login> -pass <pass> " +
                    "[-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]"
    )

    private fun logArgs(handler: ArgHandler) {
        if (handler.login != null) logger.info("Login = ${handler.login}")
        if (handler.pass != null) logger.info("Pass = ${handler.pass}")
        if (handler.res != null) logger.info("Res = ${handler.res}")
        if (handler.role != null) logger.info("Role = ${handler.role}")
        if (handler.ds != null) logger.info("Ds = ${handler.ds}")
        if (handler.de != null) logger.info("De = ${handler.de}")
        if (handler.vol != null) logger.info("Vol = ${handler.vol}")
    }

    @Suppress("ReturnCount")
    fun run(args: Array<String>): ExitCode {
        logger.info("Start program")
        val injector = Guice.createInjector()

        var exitCode: ExitCode?

        val handler = ArgHandler(args)
        logArgs(handler)
        if (!handler.isArgs() || handler.help) {
            logger.info("Arguments were not passed. Print help. Exit.")
            printHelp()
            return HELP
        }

        // Authentication step
        exitCode = authentication(handler, injector)
        if (exitCode != null) return exitCode

        // Authorization step
        exitCode = authorization(handler, injector)
        if (exitCode != null) return exitCode

        // Accounting step
        exitCode = accounting(handler, injector)
        if (exitCode != null) return exitCode

        logger.info("Success. Exit.")
        return SUCCESS
    }

    private fun authentication(handler: ArgHandler, injector: Injector): ExitCode? {
        if (!handler.needAuthentication()) {
            logger.info("Necessary arguments were not passed. Authentication is not required. Print help.")
            printHelp()
            logger.info("Success. Exit.")
            return SUCCESS
        } else logger.info("Necessary arguments available. Starting Authentication.")

        val authenService = injector.getInstance(Authentication::class.java)
        return when {
            !authenService.validateLogin(handler.login!!) -> {
                logger.error("Invalid login. Exit.")
                INVALID_LOGIN
            }
            !authenService.loginExists(handler.login!!) -> {
                logger.error("Unknown Login. Exit.")
                UNKNOWN_LOGIN
            }
            !authenService.authenticate(handler.login!!, handler.pass!!) -> {
                logger.error("Wrong password. Exit.")
                WRONG_PASS
            }
            else -> null
        }
    }

    private fun authorization(handler: ArgHandler, injector: Injector): ExitCode? {
        if (!handler.needAuthorization()) {
            logger.info("Necessary arguments were not passed. Authorization is not required.")
            logger.warn("Success. Exit.")
            return SUCCESS
        } else logger.info("Necessary arguments available. Starting Authorization")

        val authorizeService = injector.getInstance(Authorization::class.java)
        return when {
            !authorizeService.validateRole(handler.role!!) -> {
                logger.error("Unknown role. Exit.")
                UNKNOWN_ROLE
            }
            !authorizeService.hasAuthority(
                            handler.login!!,
                            Role.valueOf(handler.role!!),
                            handler.res!!) -> {
                logger.error("No access. Exit.")
                NO_ACCESS
            }
            else -> null
        }
    }

    private fun accounting(handler: ArgHandler, injector: Injector): ExitCode? {
        if (!handler.needAccounting()) {
            logger.info("Necessary arguments were not passed. Accounting is not required.")
            logger.info("Success. Exit.")
            return SUCCESS
        } else logger.info("Necessary arguments available. Starting Accounting")

        val accountingService = injector.getInstance(Accounting::class.java)
        val exitCode = when {
            !accountingService.validateVol(handler.vol!!.toIntOrNull()) -> {
                logger.info("Invalid Volume. Exit")
                INVALID_ACTIVITY
            }
            !accountingService.validateDate(handler.ds!!) -> {
                logger.info("Invalid start date. Exit.")
                INVALID_ACTIVITY
            }
            !accountingService.validateDate(handler.de!!) -> {
                logger.info("Invalid end date. Exit.")
                INVALID_ACTIVITY
            }
            else -> null
        }

        if (exitCode == null) {
            logger.info("Successful accounting. Adding activity to base.")
            accountingService.addActivity(
                    handler.login!!,
                    handler.res!!,
                    Role.valueOf(handler.role!!),
                    handler.ds!!,
                    handler.de!!,
                    handler.vol!!.toInt()
            )
        }
        return exitCode
    }
}

package com.kafedra.bd

import com.kafedra.bd.ExitCode.*
import com.kafedra.bd.domain.Activity
import com.kafedra.bd.domain.DBWrapper
import com.kafedra.bd.domain.Permission
import com.kafedra.bd.domain.User
import com.kafedra.bd.service.Accounting
import com.kafedra.bd.service.ArgHandler
import com.kafedra.bd.service.Authentication
import com.kafedra.bd.service.Authorization
import org.apache.logging.log4j.LogManager


class App(val users: List<User>, val permissions: List<Permission>, val activities: MutableList<Activity>) {
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

    fun run(args: Array<String>): ExitCode {
        logger.info("Start program")
        val dbWrapper = DBWrapper()
        if (dbWrapper.dbExists()) {
            logger.info("Using existing database.")
            dbWrapper.connect(
                System.getenv("H2_URL"),
                System.getenv("H2_LOGIN"), System.getenv("H2_PASS")
            )
        }
        else {
            logger.warn("Database does not exist. Initiating new database.")
            dbWrapper.initDatabase(
                users, permissions, System.getenv("H2_URL"),
                System.getenv("H2_LOGIN"), System.getenv("H2_PASS")
            )
        }


        val handler = ArgHandler(args)
        logArgs(handler)
        if (!handler.isArgs() || handler.help) {
            logger.info("Arguments were not passed. Print help. Exit.")
            printHelp()
            return HELP
        }


        // Authentication step
        if (!handler.needAuthentication()) {
            logger.info("Necessary arguments were not passed. Authentication is not required. Print help.")
            printHelp()
            logger.info("Success. Exit.")
            return SUCCESS
        } else logger.info("Necessary arguments available. Starting Authentication.")

        val authenService = Authentication(dbWrapper)
        when {
            !authenService.validateLogin(handler.login!!) -> {
                logger.error("Invalid login. Exit.")
                return INVALID_LOGIN
            }
            !authenService.loginExists(handler.login!!) -> {
                logger.error("Unknown Login. Exit.")
                return UNKNOWN_LOGIN
            }
            !authenService.authenticate(handler.login!!, handler.pass!!) -> {
                logger.error("Wrong password. Exit.")
                return WRONG_PASS
            }
        }


        // Authorization step
        if (!handler.needAuthorization()) {
            logger.info("Necessary arguments were not passed. Authorization is not required.")
            logger.warn("Success. Exit.")
            return SUCCESS
        } else logger.info("Necessary arguments available. Starting Authorization")

        val authorizeService = Authorization(dbWrapper)
        when {
            !authorizeService.validateRole(handler.role!!) -> {
                logger.error("Unknown role. Exit.")
                return UNKNOWN_ROLE
            }
            !authorizeService.hasPermission(handler.res!!, Role.valueOf(handler.role!!), handler.login!!) -> {
                logger.error("No access. Exit.")
                return NO_ACCESS
            }
        }


        // Accounting step
        if (!handler.needAccounting()) {
            logger.info("Necessary arguments were not passed. Accounting is not required.")
            logger.info("Success. Exit.")
            return SUCCESS
        } else logger.info("Necessary arguments available. Starting Accounting")

        val accountingService = Accounting(dbWrapper)
        when {
            !accountingService.validateVol(handler.vol!!.toIntOrNull()) -> {
                logger.info("Invalid Volume. Exit")
                return INVALID_ACTIVITY
            }
            !accountingService.validateDate(handler.ds!!) -> {
                logger.info("Invalid start date. Exit.")
                return INVALID_ACTIVITY
            }
            !accountingService.validateDate(handler.de!!) -> {
                logger.info("Invalid end date. Exit.")
                return INVALID_ACTIVITY
            }

        }

        logger.info("Successfull accounting. Adding activity to base.")
        accountingService.addActivity(
            dbWrapper.getUser(handler.login!!), handler.res!!,
            Role.valueOf(handler.role!!), handler.ds!!, handler.de!!, handler.vol!!.toInt()
        )


        logger.info("Success. Exit.")
        return SUCCESS
    }

}
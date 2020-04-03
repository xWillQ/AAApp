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
        if (handler.vol != null) logger.info("vol = ${handler.vol}")
    }

    fun run(args: Array<String>): ExitCode {
        val dbWrapper = DBWrapper()
        if (dbWrapper.dbExists()) dbWrapper.connect(System.getenv("H2_URL"), 
                System.getenv("H2_LOGIN"), System.getenv("H2_PASS"))
        else dbWrapper.initDatabase(users, permissions, System.getenv("H2_URL"),
                System.getenv("H2_LOGIN"), System.getenv("H2_PASS"))


        val handler = ArgHandler(args)
        logArgs(handler)
        if (!handler.isArgs() || handler.help) {
            printHelp()
            return HELP
        }


        // Authentication step
        logger.info("Start program")
        if (!handler.needAuthentication()) {
            printHelp()
            return SUCCESS
        } else logger.info("Args available. Start Authentication")

        val authenService = Authentication(dbWrapper)
        when {
            !authenService.validateLogin(handler.login!!) -> return INVALID_LOGIN
            !authenService.loginExists(handler.login!!) -> return UNKNOWN_LOGIN
            !authenService.authenticate(handler.login!!, handler.pass!!) -> return WRONG_PASS
        }


        // Authorization step
        if (!handler.needAuthorization()) {
            logger.warn("Success. Exit.")
            return SUCCESS
        } else logger.info("Args available. Start Authorization")

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
            logger.info("Success. Exit.")
            return SUCCESS
        } else logger.info("Args available. Start Accounting")

        val accountingService = Accounting(dbWrapper)
        if (!accountingService.validateVol(handler.vol!!.toIntOrNull()) ||
                !accountingService.validateDate(handler.ds!!) ||
                !accountingService.validateDate(handler.de!!)) {
            logger.error("Invalid Activity. Exit.")
            return INVALID_ACTIVITY
        }

        accountingService.addActivity(users.first { it.login == handler.login }, handler.res!!,
                Role.valueOf(handler.role!!), handler.ds!!, handler.de!!, handler.vol!!.toInt())


        logger.info("Success. Exit.")
        return SUCCESS
    }

}
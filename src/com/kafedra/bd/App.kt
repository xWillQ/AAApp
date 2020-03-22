package com.kafedra.bd

import com.kafedra.bd.ExitCode.*
import com.kafedra.bd.service.Accounting
import com.kafedra.bd.service.ArgHandler
import com.kafedra.bd.service.Authentication
import com.kafedra.bd.service.Authorization

class App {
    companion object {
        private fun printHelp() = println("Usage: app.jar [-h] [-login <login> -pass <pass> " +
                "[-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")

        fun run(args: Array<String>): ExitCode {
            val handler = ArgHandler(args)
            if (!handler.isArgs() || handler.help) {
                printHelp()
                return HELP
            }


            if (!handler.needAuthentication()) {
                printHelp()
                return SUCCESS
            }

            when {
                !Authentication.validateLogin(handler.login!!) -> return INVALID_LOGIN
                !Authentication.loginExists(handler.login!!) -> return UNKNOWN_LOGIN
                !Authentication.authenticate(handler.login!!, handler.pass!!) -> return WRONG_PASS
            }


            if (!handler.needAuthorization()) return SUCCESS

            when {
                !Authorization.validateRole(handler.role!!) -> return UNKNOWN_ROLE
                !Authorization.hasPermission(handler.res!!, Role.valueOf(handler.role!!),
                        handler.login!!) -> return NO_ACCESS
            }


            if (!handler.needAccounting()) return SUCCESS

            if (!Accounting.validateVol(handler.vol!!.toIntOrNull()) ||
                    !Accounting.validateDate(handler.ds!!) ||
                    !Accounting.validateDate(handler.de!!)) return INVALID_ACTIVITY

            Accounting.addActivity(handler.login!!, handler.res!!, Role.valueOf(handler.role!!),
                    handler.ds!!, handler.de!!, handler.vol!!.toInt())


            return SUCCESS
        }
    }
}
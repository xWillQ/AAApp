import kotlin.system.exitProcess

data class User(val login: String, val pass: String)

class ArgHandler(args: Array<String>) {
    private val empty = args.isEmpty()
    private val help = !empty && args[0] == "-h"
    private val authentication = if (args.size >= 2) args[0] == "-login" && args[2] == "-pass" else false
    private val authorization = if (args.size >= 8) args[4] == "-res" && args[6] == "-role" else false

    fun needHelp() = help

    fun isArgs() = !empty

    fun needAuthentication() = authentication

    fun needAuthorization() = authorization

}

val users = listOf<User>(User("vasya", "123"), User("admin", "admin"), User("q", "?!#"), User("abcdefghij", "qwerty"))

fun printHelp() =
        println("Usage: app.jar [-h] [-login <login> -pass <pass> [-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")

fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))

fun loginExists(login: String) = users.any { it.login == login }

fun authenticate(login: String, pass: String) = users.any { it.login == login && it.pass == pass }

fun validateRole(role: String) = (role == "READ" || role == "WRITE" || role == "EXECUTE")

fun hasPermission(res: String, role: String, user: String) = (res == "A" && role == "READ" && user == "vasya")

fun main(args: Array<String>) {
    val handler = ArgHandler(args)
    if (!handler.isArgs() || handler.needHelp()) {
        printHelp()
        exitProcess(1)
    }

    if (!handler.needAuthentication()) {
        printHelp()
        exitProcess(0)
    }

    when {
        !validateLogin(args[1]) -> exitProcess(2)
        !loginExists(args[1]) -> exitProcess(3)
        !authenticate(args[1], args[3]) -> exitProcess(4)
    }

    if (!handler.needAuthorization()) exitProcess(0)

    when {
        !validateRole(args[7]) -> exitProcess(5)
        !hasPermission(args[5], args[7], args[1]) -> exitProcess(6)
    }

}
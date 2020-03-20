import kotlin.system.exitProcess

data class User(val login: String, val pass: String)

data class Permission(val res: String, val role: String, val user: User)

class ArgHandler(args: Array<String>) {
    private val empty = args.isEmpty()
    private val help = !empty && args[0] == "-h"
    private val authentication = if (args.size >= 2) args[0] == "-login" && args[2] == "-pass" else false
    private val authorization = if (args.size >= 8) args[4] == "-role" && args[6] == "-res" else false
    private val accounting = if (args.size >= 14) args[8] == "-ds" && args[10] == "-de" && args[12] == "-vol" else false

    fun needHelp() = help

    fun isArgs() = !empty

    fun needAuthentication() = authentication

    fun needAuthorization() = authorization

    fun needAccounting() = accounting

}

val users = listOf(User("vasya", "123"), User("admin", "admin"), User("q", "?!#"), User("abcdefghij", "qwerty"))

val permissions = listOf(Permission("A", "READ", users[0]), Permission("A.B.C", "WRITE", users[0]),
        Permission("A.B", "EXECUTE", users[1]), Permission("A", "READ", users[1]),
        Permission("A.B", "WRITE", users[1]), Permission("A.B.C", "READ", users[1]),
        Permission("B", "EXECUTE", users[2]), Permission("A.A.A", "EXECUTE", users[0]))

fun printHelp() =
        println("Usage: app.jar [-h] [-login <login> -pass <pass> [-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")

fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))

fun loginExists(login: String) = users.any { it.login == login }

fun authenticate(login: String, pass: String) = users.any { it.login == login && it.pass == pass }

fun validateRole(role: String) = (role == "READ" || role == "WRITE" || role == "EXECUTE")

fun hasPermission(res: String, role: String, user: String): Boolean {
    return permissions.any { res.contains(Regex("^" + it.res + "\\b")) && it.role == role && it.user.login == user }
}

fun validateVol(vol: Int) = vol > 0

fun validateDate(date: String): Boolean {
    return date.matches(Regex("\\d{4}-((((0[13578])|(1[02]))-(0[1-9]|[12][0-9]|3[01]))|(((0[469])|(11))-(0[1-9]|[12][0-9]|(30)))|(02-(0[1-9]|[12][0-9])))"))
}

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
        !validateRole(args[5]) -> exitProcess(5)
        !hasPermission(args[7], args[5], args[1]) -> exitProcess(6)
    }

}
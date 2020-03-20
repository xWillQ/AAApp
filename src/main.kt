import kotlin.system.exitProcess

data class User(val login: String, val pass: String)
data class Permission(val res: String, val role: String, val user: User)
data class Activity(val user: User, val res: String, val role: String, val ds: String, val de: String, val vol: Int)


val users = listOf(User("vasya", "123"), User("admin", "admin"), User("q", "?!#"), User("abcdefghij", "qwerty"))
val permissions = listOf(Permission("A", "READ", users[0]), Permission("A.B.C", "WRITE", users[0]),
        Permission("A.B", "EXECUTE", users[1]), Permission("A", "READ", users[1]),
        Permission("A.B", "WRITE", users[1]), Permission("A.B.C", "READ", users[1]),
        Permission("B", "EXECUTE", users[2]), Permission("A.A.A", "EXECUTE", users[0]))
val activities = mutableListOf<Activity>()


fun printHelp() =
        println("Usage: app.jar [-h] [-login <login> -pass <pass> [-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")

fun main(args: Array<String>) {
    val handler = ArgHandler(args)
    if (!handler.isArgs()) {
        printHelp()
        exitProcess(1)
    }

    if (!handler.needAuthentication()) {
        printHelp()
        exitProcess(0)
    }

    when {
        !validateLogin(handler.login!!) -> exitProcess(2)
        !loginExists(handler.login!!) -> exitProcess(3)
        !authenticate(handler.login!!, handler.pass!!) -> exitProcess(4)
    }

    if (!handler.needAuthorization()) exitProcess(0)

    when {
        !validateRole(handler.role!!) -> exitProcess(5)
        !hasPermission(handler.res!!, handler.role!!, handler.login!!) -> exitProcess(6)
    }

    if (!handler.needAccounting()) exitProcess(0)

    if (!validateVol(handler.vol!!.toIntOrNull()) || !validateDate(handler.ds!!) || !validateDate(handler.de!!)) exitProcess(7)

    activities += Activity(users.first { it.login == handler.login }, handler.res!!, handler.role!!, handler.ds!!, handler.de!!, handler.vol!!.toInt())

}
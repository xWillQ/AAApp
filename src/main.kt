import kotlin.system.exitProcess

data class User(val login: String, val pass: String)

val users = listOf<User>(User("vasya", "123"), User("admin", "admin"), User("q", "?!#"), User("abcdefghij", "qwerty"))

fun printHelp() {
    println("Usage: app.jar [-h] [-login <login> -pass <pass> [-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")
}

fun needAuthentication(args: Array<String>): Boolean {
    return args[0] == "-login" && args[2] == "-pass"
}

fun validateLogin(login: String): Boolean {
    return login.matches(Regex("[a-z]{1,10}"))
}

fun loginExists(login: String): Boolean {
    return login == "vasya"
}

fun authenticate(login: String, pass: String): Boolean {
    return login == "vasya" && pass == "123"
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printHelp()
        exitProcess(1)
    }
    if (args[0] == "-h") {
        printHelp()
        exitProcess(1)
    }

    if (!needAuthentication(args)) {
        printHelp()
        exitProcess(0)
    }
    if (!validateLogin(args[1])) exitProcess(2)
    if (!loginExists(args[1])) exitProcess(3)
    if (!authenticate(args[1], args[3])) exitProcess(4)

}
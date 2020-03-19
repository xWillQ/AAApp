import kotlin.system.exitProcess

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

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printHelp()
        exitProcess(1)
    }
    if (args[0] == "-h") {
        printHelp()
        exitProcess(1)
    }

    if (needAuthentication(args)) {
        if (validateLogin(args[1])) exitProcess(0)
        else exitProcess(2)
    }

    printHelp()
    exitProcess(0)

}
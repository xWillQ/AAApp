import kotlin.system.exitProcess

fun printHelp() {
    println("Usage: app.jar [-h] [-login <login> -pass <pass> [-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")
    exitProcess(1)
}

fun main(args: Array<String>) {
    if (args.isEmpty()) exitProcess(0)
}
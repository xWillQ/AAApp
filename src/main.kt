import kotlin.system.exitProcess

fun printHelp() {
    println("Usage: app.jar [-h] [-login <login> -pass <pass> [-res <str> -role <str> [-ds <yyyy-mm-dd> -de <yyyy-mm-dd> -vol <int>] ] ]")
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

    printHelp()
    exitProcess(0)

}
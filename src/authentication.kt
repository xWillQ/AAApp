fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))

fun loginExists(login: String) = users.any { it.login == login }

fun authenticate(login: String, pass: String) = users.any { it.login == login && it.pass == pass }
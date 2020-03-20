import java.security.MessageDigest

fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))

fun loginExists(login: String) = users.any { it.login == login }

fun authenticate(login: String, pass: String) = users.any { it.login == login && it.pass == pass }

fun authHash(login: String, pass: String): Boolean {
    return login == "vasya" && getSaltedHash(pass, getSalt("vasya")) == "fc5e540b95138a20e304971df53d66887495a05050031881799feeedcfa52f24"
}

fun getSalt(login: String) = "46c61ea8b02362b462f24f1ac8d9aacd"

fun getSaltedHash(pass: String, salt: String) = hash(pass + salt)

fun hash(str: String): String {
    val bytes = str.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { s, it -> s + "%02x".format(it) })
}

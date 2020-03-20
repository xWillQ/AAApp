fun validateRole(role: String) = (role == "READ" || role == "WRITE" || role == "EXECUTE")

fun hasPermission(res: String, role: String, user: String): Boolean {
    return permissions.any { res.contains(Regex("^" + it.res + "\\b")) && it.role == role && it.user.login == user }
}
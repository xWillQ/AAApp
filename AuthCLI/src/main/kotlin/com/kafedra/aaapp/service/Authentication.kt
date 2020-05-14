package com.kafedra.aaapp.service

import com.google.inject.Inject
import com.kafedra.aaapp.dao.UserDao
import java.security.MessageDigest

class Authentication {
    @Inject lateinit var dao: UserDao

    fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))

    fun loginExists(login: String) = dao.loginExists(login)

    fun authenticate(login: String, pass: String): Boolean {
        val user = dao.getUser(login)
        return user.hash == getSaltedHash(pass, getSalt(user.login))
    }

    private fun getSalt(login: String): String = dao.getUser(login).salt

    private fun getSaltedHash(pass: String, salt: String) = hash(pass + salt)

    private fun hash(str: String): String {
        val bytes = str.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { s, it -> s + "%02x".format(it) })
    }
}

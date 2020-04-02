package com.kafedra.bd.service

import com.kafedra.bd.domain.DBWrapper
import com.kafedra.bd.domain.User
import java.security.MessageDigest

class Authentication(val dbWrapper: DBWrapper) {

    fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))


    fun authenticate(login: String, pass: String): Boolean {
        val user = dbWrapper.getUser(login)
        return user.hash == getSaltedHash(pass, getSalt(user.login))
    }

    private fun getSalt(login: String): String = dbWrapper.getUser(login).salt
    private fun getSaltedHash(pass: String, salt: String) = hash(pass + salt)

    private fun hash(str: String): String {
        val bytes = str.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { s, it -> s + "%02x".format(it) })

    }
}


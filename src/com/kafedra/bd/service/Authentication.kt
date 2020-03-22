package com.kafedra.bd.service

import com.kafedra.bd.users
import java.security.MessageDigest

class Authentication {
    companion object {
        fun validateLogin(login: String) = login.matches(Regex("[a-z]{1,10}"))

        fun loginExists(login: String) = users.any { it.login == login }


        fun authenticate(login: String, pass: String) = users.any {
            it.login == login &&
                    it.hash == getSaltedHash(pass, getSalt(it.login))
        }

        private fun getSalt(login: String): String {
            for (u in users) {
                if (u.login == login) return u.salt
            }
            return ""
        }

        private fun getSaltedHash(pass: String, salt: String) = hash(pass + salt)

        private fun hash(str: String): String {
            val bytes = str.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { s, it -> s + "%02x".format(it) })
        }
    }

}
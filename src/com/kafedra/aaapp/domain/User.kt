package com.kafedra.aaapp.domain

data class User(val login: String, val salt: String, val hash: String)
package com.kafedra.bd.domain

data class User(val login: String, val salt: String, val hash: String)
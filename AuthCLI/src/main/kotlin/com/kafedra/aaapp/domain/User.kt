package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose

data class User(
    @Expose val id: Int,
    @Expose val login: String,
    val salt: String,
    val hash: String
)

package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose
import com.kafedra.aaapp.Role

data class Activity(
    @Expose val id: Int,
    val user: User,
    val authorityId: Int,
    val res: String,
    val role: Role,
    @Expose val ds: String,
    @Expose val de: String,
    @Expose val vol: Int
)

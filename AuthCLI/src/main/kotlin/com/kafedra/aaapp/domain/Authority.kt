package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose
import com.kafedra.aaapp.Role

data class Authority(
        @Expose val id: Int,
        val user: String,
        @Expose val role: Role,
        @Expose val res: String
)

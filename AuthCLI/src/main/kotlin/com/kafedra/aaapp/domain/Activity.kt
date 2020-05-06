package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose

data class Activity(
        @Expose val id: Int,
        val authority: Authority,
        @Expose val ds: String,
        @Expose val de: String,
        @Expose val vol: Int
)

package com.kafedra.aaapp.domain

import com.kafedra.aaapp.Role

data class Activity(
        val id: Int,
        val authority: Authority,
        val ds: String,
        val de: String,
        val vol: Int)

package com.kafedra.aaapp.domain

import com.kafedra.aaapp.Role

data class Authority(val id: Int, val user: String, val role: Role, val res: String)

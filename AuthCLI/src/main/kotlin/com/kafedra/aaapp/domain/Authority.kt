package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose
import com.kafedra.aaapp.Role

class Authority {
    @Expose var id: Int = 0
    var user: User? = null
    @Expose var role: Role? = null
    @Expose var res: String = ""
}

package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose

class User {
    @Expose var id: Int = 0
    @Expose var login: String = ""
    var salt: String = ""
    var hash: String = ""
}

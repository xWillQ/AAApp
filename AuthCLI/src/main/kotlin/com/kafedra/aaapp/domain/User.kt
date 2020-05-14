package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose

class User {
    @Expose var id: Int = 0
    @Expose var login: String = ""
    var salt: String = ""
    var hash: String = ""
    var version: Long = 0

    constructor() {}

    constructor(id: Int, login: String, salt: String, hash: String) {
        this.id = id
        this.login = login
        this.salt = salt
        this.hash = hash
    }
}

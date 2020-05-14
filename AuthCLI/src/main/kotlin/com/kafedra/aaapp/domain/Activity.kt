package com.kafedra.aaapp.domain

import com.google.gson.annotations.Expose
import com.kafedra.aaapp.Role

class Activity {
    @Expose var id: Int = 0
    var user: User? = null
    var authority: Authority? = null
    var res: String = ""
    var role: Role? = null
    @Expose var ds: String = ""
    @Expose var de: String = ""
    @Expose var vol: Int = 0
}

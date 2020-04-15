package com.kafedra.aaapp.service

import com.kafedra.aaapp.Role
import com.kafedra.aaapp.domain.Activity
import com.kafedra.aaapp.domain.DBWrapper
import com.kafedra.aaapp.domain.User
import org.apache.logging.log4j.LogManager

class Accounting(private val dbWrapper: DBWrapper) {

    private val logger = LogManager.getLogger()
    fun validateVol(vol: Int?) = if (vol != null) vol > 0 else false

    fun validateDate(date: String) =
        date.matches(
            Regex(
                "\\d{4}-((((0[13578])|(1[02]))-(0[1-9]|[12][0-9]|3[01]))|" +
                        "(((0[469])|(11))-(0[1-9]|[12][0-9]|(30)))|(02-(0[1-9]|[12][0-9])))"
            )
        )

    @Suppress("LongParameterList")
    fun addActivity(
        user: User,
        res: String,
        role: Role,
        ds: String,
        de: String,
        vol: Int
    ) {
        dbWrapper.addActivity(Activity(user, res, role, ds, de, vol))
        logger.info("Add Activity")
    }
}

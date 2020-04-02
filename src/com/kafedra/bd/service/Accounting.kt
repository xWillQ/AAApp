package com.kafedra.bd.service

import com.kafedra.bd.Role
import com.kafedra.bd.domain.Activity
import com.kafedra.bd.domain.DBWrapper
import com.kafedra.bd.domain.User
import org.apache.logging.log4j.LogManager

class Accounting {
    companion object {
        val dbWrapper = DBWrapper()
        private val logger = LogManager.getLogger()
        fun validateVol(vol: Int?) = if (vol != null) vol > 0 else false

        fun validateDate(date: String) =
                date.matches(Regex("\\d{4}-((((0[13578])|(1[02]))-(0[1-9]|[12][0-9]|3[01]))|" +
                        "(((0[469])|(11))-(0[1-9]|[12][0-9]|(30)))|(02-(0[1-9]|[12][0-9])))"))

        fun addActivity(user: User, res: String,
                        role: Role, ds: String, de: String, vol: Int) {
            dbWrapper.addActivity(Activity(user, res, role, ds, de, vol))
            logger.info("Add Activity")
        }
    }
}
package com.kafedra.aaapp.service

import com.kafedra.aaapp.domain.Activity
import com.kafedra.aaapp.domain.DBWrapper
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

    fun addActivity(activity: Activity) {
        dbWrapper.addActivity(activity)
        logger.info("Add Activity")
    }
}

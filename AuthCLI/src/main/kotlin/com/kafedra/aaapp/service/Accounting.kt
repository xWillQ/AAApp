package com.kafedra.aaapp.service

import com.google.inject.Inject
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.dao.ActivityDao
import com.kafedra.aaapp.dao.AuthorityDao
import com.kafedra.aaapp.dao.UserDao
import com.kafedra.aaapp.domain.Activity
import org.apache.logging.log4j.LogManager

class Accounting {
    @Inject lateinit var activityDao: ActivityDao
    @Inject lateinit var userDao: UserDao
    @Inject lateinit var authorityDao: AuthorityDao
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
    fun addActivity(login: String, res: String, role: Role, ds: String, de: String, vol: Int) {
        val activity = Activity()
        activity.user = userDao.getUser(login)
        activity.authority = authorityDao.getAuthority(authorityDao.getAuthorityId(login, role, res))[0]
        activity.res = res
        activity.role = role
        activity.ds = ds
        activity.de = de
        activity.vol = vol
        activityDao.addActivity(activity)
        logger.info("Add Activity")
    }
}

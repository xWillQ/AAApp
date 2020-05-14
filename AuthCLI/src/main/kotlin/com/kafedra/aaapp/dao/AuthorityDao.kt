package com.kafedra.aaapp.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.aaapp.domain.Authority
import com.kafedra.aaapp.domain.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AuthorityDao {
    @Inject lateinit var sessionProvider : HibernateProvider
    val logger: Logger = LogManager.getLogger()

    private fun generateResourceRegex(res: String) : Regex {
        var resRegex = res
        while (resRegex.contains(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)")))
            resRegex = resRegex.replace(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)"), "(\\\\$1)?")
        return Regex("^$resRegex$")
    }

    fun hasAuthority(login: String, role: Role, res: String) : Boolean {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        // TODO: find better solution
        // This is a bodge, should be done with one query and without pulling all authorities from DB
        logger.info("Querying authorities with user = $login and role = ${role.role}")
        val authorityList = session.createQuery("FROM Authority WHERE user.login = '$login' and role = '${role.role}'", Authority::class.java).resultList

        var result = false
        val regex = generateResourceRegex(res)
        logger.info("Matching received authorities against $regex")
        for (a in authorityList) {
            if (a.res.matches(regex)) {
                logger.info("Found matching authority")
                result = true
                break
            }
        }

        logger.info("Closing hibernate session")
        session.close()

        return result
    }

    fun getAuthorityId(login: String, role: Role, res: String)  : Int {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        // TODO: find better solution
        // This is a bodge, should be done with one query and without pulling all authorities from DB
        logger.info("Querying authorities with user = $login and role = ${role.role}")
        val authorityList = session.createQuery("FROM Authority WHERE user.login = '$login' and role = '${role.role}'", Authority::class.java).resultList

        var result = 0
        val regex = generateResourceRegex(res)
        logger.info("Matching received authorities against $regex")
        for (a in authorityList) {
            if (a.res.matches(regex)) {
                logger.info("Found matching authority")
                result = a.id
                break
            }
        }

        logger.info("Closing hibernate session")
        session.close()

        return result
    }

    fun getAuthority(id: Int) : List<Authority> {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        val authorityList = if (id != 0) {
            logger.info("Querying authority with id = $id")
            val authority = session.get(Authority::class.java, id)
            if (authority != null) {
                logger.info("Authority exists, returning requested authority")
                listOf(authority)
            } else {
                logger.info("Authority doesn't exists, returning empty list")
                listOf()
            }
        } else {
            logger.info("Id = 0, querying all authorities")
            val query = session.createQuery("FROM Authority", Authority::class.java)
            query.resultList
        }

        logger.info("Closing hibernate session")
        session.close()

        return authorityList
    }

    fun getAuthorityByUser(userId: Int): List<Authority> {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        logger.info("Querying authorities with userId = $userId")
        val query = session.createQuery("FROM Authority WHERE userId = $userId", Authority::class.java)

        val authoritiesList = query.resultList
        logger.info("Received ${authoritiesList.size} authorities")

        logger.info("Closing hibernate session")
        session.close()

        return authoritiesList
    }
}
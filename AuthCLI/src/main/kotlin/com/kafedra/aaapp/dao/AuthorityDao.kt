package com.kafedra.aaapp.dao

import com.google.inject.Inject
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.aaapp.domain.Authority
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AuthorityDao {
    @Inject lateinit var sessionProvider: HibernateProvider
    val logger: Logger = LogManager.getLogger()

    fun hasAuthority(login: String, role: Role, res: String): Boolean {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        logger.info("Counting authorities with user = $login and role = ${role.role} and res contains $res")
        val count = session.createQuery(
                "SELECT count(*) " +
                        "FROM Authority " +
                        "WHERE user.login = '$login' and role = '${role.role}' and " +
                        "(res = '$res' or '$res' LIKE concat(res, '.%'))"
        ).singleResult as Long

        logger.info("Closing hibernate session")
        session.close()

        return count > 0
    }

    fun getAuthorityId(login: String, role: Role, res: String): Int {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        logger.info("Querying authorities with user = $login and role = ${role.role} and res contains $res")
        val authorityList = session.createQuery(
                        "FROM Authority " +
                        "WHERE user.login = '$login' and role = '${role.role}' and " +
                        "(res = '$res' or '$res' LIKE concat(res, '.%'))",
                Authority::class.java
        ).resultList

        val result = if (authorityList.size > 0) authorityList[0].id
        else 0

        logger.info("Closing hibernate session")
        session.close()

        return result
    }

    fun getAuthority(id: Int): List<Authority> {
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
        val query = session.createQuery(
                "FROM Authority WHERE userId = $userId",
                Authority::class.java
        )

        val authoritiesList = query.resultList
        logger.info("Received ${authoritiesList.size} authorities")

        logger.info("Closing hibernate session")
        session.close()

        return authoritiesList
    }
}

package com.kafedra.aaapp.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.aaapp.domain.Authority
import com.kafedra.aaapp.domain.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.sql.Connection

class UserDao {
    @Inject lateinit var sessionProvider : HibernateProvider
    val logger: Logger = LogManager.getLogger()

    fun loginExists(login: String) : Boolean {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        logger.info("Querying amount of users with login = $login")
        val count = session.createQuery("SELECT count(*) FROM User where login = '$login'").singleResult as Long

        if (count == 0L) logger.info("User with login = $login exists")
        else logger.info("User with login = $login doesn't exist")

        logger.info("Closing hibernate session")
        session.close()

        return count > 0
    }

    fun getUser(login: String) : User {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()
        logger.info("Querying user with login = $login")
        val query = session.createQuery("FROM User WHERE login = '$login'", User::class.java)
        var user: User
        try {
            user = query.singleResult
            logger.info("User exists, returning requested user")
        } catch (e: javax.persistence.NoResultException) {
            logger.info("User doesn't exist, returning empty user object")
            user = User()
        }
        logger.info("Closing hibernate session")
        session.close()
        return user
    }

    fun getUser(id: Int) : List<User> {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        val userList = if (id != 0) {
            logger.info("Querying user with id = $id")
            val user = session.get(User::class.java, id)
            if (user != null) {
                logger.info("User exists, returning requested user")
                listOf(user)
            } else {
                logger.info("User doesn't exists, returning empty list")
                listOf()
            }
        } else {
            logger.info("Id = 0, querying all users")
            val query = session.createQuery("FROM User", User::class.java)
            query.resultList
        }

        logger.info("Closing hibernate session")
        session.close()

        return userList
    }
}
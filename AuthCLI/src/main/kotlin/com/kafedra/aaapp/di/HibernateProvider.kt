package com.kafedra.aaapp.di

import com.google.inject.Provider
import com.google.inject.Singleton
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

@Singleton
class HibernateProvider : Provider<SessionFactory> {
    private var sessionFactory: SessionFactory
    val logger: Logger = LogManager.getLogger()

    init {
        val cfg = Configuration().configure("hibernate.cfg.xml")
        val url = System.getenv("JDBC_DATABASE_URL")
        if (url != "" && url != null) {
            logger.info("Reconfiguring hibernate to use postgres")
            cfg.setProperty("hibernate.connection.url", url)
            cfg.setProperty("hibernate.connection.username", System.getenv("JDBC_DATABASE_USERNAME"))
            cfg.setProperty("hibernate.connection.password", System.getenv("JDBC_DATABASE_PASSWORD"))
            cfg.setProperty("hibernate.connection.driverClass", "org.postgresql.Driver")
        }
        sessionFactory = cfg.buildSessionFactory()
    }

    override fun get(): SessionFactory {
        return sessionFactory
    }
}

package com.kafedra.aaapp.di

import com.google.inject.Provider
import com.google.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

@Singleton
class HibernateProvider : Provider<SessionFactory> {
    private var sessionFactory: SessionFactory

    init {
        val cfg = Configuration().configure()
        val url = System.getenv("DATABASE_URL")
        if (url != null) cfg.setProperty("connection.url", url)
        sessionFactory = cfg.buildSessionFactory()
    }

    override fun get(): SessionFactory {
        return sessionFactory
    }
}

package com.kafedra.aaapp.di

import com.google.inject.Provider
import com.google.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

@Singleton
class HibernateProvider : Provider<SessionFactory> {
    private val sessionFactory: SessionFactory = Configuration().configure().buildSessionFactory()

    override fun get(): SessionFactory {
        return sessionFactory
    }
}

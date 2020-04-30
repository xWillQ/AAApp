package com.kafedra.aaapp.injector

import com.google.inject.MembersInjector
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.lang.reflect.Field


internal class Log4JMembersInjector<T>(private val field: Field) : MembersInjector<T> {
    private val logger: Logger = LogManager.getLogger(field.declaringClass)
    override fun injectMembers(t: T) {
        try {
            field.set(t, logger)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    init {
        field.isAccessible = true
    }
}
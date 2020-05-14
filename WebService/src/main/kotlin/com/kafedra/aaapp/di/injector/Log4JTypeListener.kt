package com.kafedra.aaapp.di.injector

import com.google.inject.TypeLiteral
import com.google.inject.spi.TypeEncounter
import com.google.inject.spi.TypeListener
import org.apache.logging.log4j.Logger


internal class Log4JTypeListener : TypeListener {
    override fun <T> hear(typeLiteral: TypeLiteral<T>, typeEncounter: TypeEncounter<T>) {
        var clazz = typeLiteral.rawType
        while (clazz != null) {
            for (field in clazz.declaredFields) {
                if (field.type === Logger::class.java &&
                        field.isAnnotationPresent(InjectLogger::class.java)) {
                    typeEncounter.register(Log4JMembersInjector<T>(field))
                }
            }
            clazz = clazz.superclass
        }
    }
}
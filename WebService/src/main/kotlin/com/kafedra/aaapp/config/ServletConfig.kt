package com.kafedra.aaapp.config

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule
import com.kafedra.aaapp.servlet.*


class ServletConfig() : GuiceServletContextListener() {
    override fun getInjector(): Injector = Guice.createInjector(object : ServletModule() {
        override fun configureServlets() {
            super.configureServlets()
            serve("/echo/get").with(GetListener::class.java)
            serve("/echo/post").with(PostListener::class.java)
            serve("/echo/*").with(EchoListener::class.java)

            serve("/ajax/user").with(UserServlet::class.java)
            serve("/ajax/authority").with(AuthorityServlet::class.java)
        }
    })
}
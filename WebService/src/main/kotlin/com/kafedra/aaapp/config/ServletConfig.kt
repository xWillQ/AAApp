package com.kafedra.aaapp.config

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.matcher.Matchers
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule
import com.kafedra.aaapp.di.injector.Log4JTypeListener
import com.kafedra.aaapp.filter.CharsetFilter
import com.kafedra.aaapp.servlet.*
import org.flywaydb.core.Flyway

class ServletConfig : GuiceServletContextListener() {
    override fun getInjector(): Injector = Guice.createInjector(object : ServletModule() {
        override fun configureServlets() {
            super.configureServlets()
            bindListener(Matchers.any(), Log4JTypeListener())
            filter("/*").through(CharsetFilter::class.java)

            serve("/echo/get").with(GetListener::class.java)
            serve("/echo/post").with(PostListener::class.java)
            serve("/echo/*").with(EchoListener::class.java)

            serve("/ajax/user").with(UserServlet::class.java)
            serve("/ajax/authority").with(AuthorityServlet::class.java)
            serve("/ajax/activity").with(ActivityServlet::class.java)

            val url = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:h2:./aaa;MV_STORE=FALSE"
            val login = System.getenv("JDBC_DATABASE_USERNAME") ?: "se"
            val pass = System.getenv("JDBC_DATABASE_PASSWORD") ?: ""
            Flyway.configure().dataSource(url, login, pass).locations("classpath:db").load().migrate()
        }
    })
}

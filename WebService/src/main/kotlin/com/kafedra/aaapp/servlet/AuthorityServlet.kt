package com.kafedra.aaapp.servlet

import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.aaapp.domain.DBWrapper
import com.kafedra.aaapp.injector.InjectLogger
import org.apache.logging.log4j.Logger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class AuthorityServlet: HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger
    @Inject
    lateinit var gsonProvider: GSONProvider
    @Inject
    lateinit var wrapper: DBWrapper

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /ajax/authority")
        response.writer.print("Authority")
    }
}
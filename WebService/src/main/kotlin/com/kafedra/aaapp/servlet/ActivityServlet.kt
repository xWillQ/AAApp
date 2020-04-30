package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import com.kafedra.aaapp.injector.InjectLogger
import org.apache.logging.log4j.Logger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class ActivityServlet: HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /ajax/user")
        response.writer.print("Activity")
    }
}
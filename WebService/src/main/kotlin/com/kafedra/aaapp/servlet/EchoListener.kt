package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import com.kafedra.aaapp.injector.InjectLogger
import org.apache.logging.log4j.Logger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class EchoListener: HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        logger.warn("Got get request to not existing page ${request.requestURL}, printing 404")
        response.writer.print("404 not found")
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        logger.warn("Got post request to not existing page ${request.requestURL}, printing 404")
        response.writer.print("404 not found")
    }
}
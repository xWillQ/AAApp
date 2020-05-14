package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import com.kafedra.aaapp.di.injector.InjectLogger
import org.apache.logging.log4j.Logger
import java.net.URLEncoder
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class PostListener: HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /echo/post")

        var input = request.getParameter("input")
        if (input == null) {
            logger.info("Input isn't set, setting to default value")
            input = "Hello"
        }
        logger.info("input = $input")

        input = URLEncoder.encode(input, "utf-8")

        logger.info("Redirecting to /echo/get?id=$input")
        response.sendRedirect("/echo/get?id=$input")
    }
}
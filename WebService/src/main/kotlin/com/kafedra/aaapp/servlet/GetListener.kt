package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import com.kafedra.aaapp.di.injector.InjectLogger
import java.net.URLDecoder
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.logging.log4j.Logger

@Singleton
class GetListener : HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /echo/get")

        val id = URLDecoder.decode(request.getParameter("id").toString(), "utf-8")
        logger.info("id = $id")

        request.setAttribute("id", id)

        logger.info("Redirecting to response page")
        request.getRequestDispatcher("../response.jsp").forward(request, response)
    }
}

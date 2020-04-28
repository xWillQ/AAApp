package com.kafedra.aaapp.servlet

import java.net.URLDecoder
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "GetListener", urlPatterns = ["echo/get"], loadOnStartup = 1)
class GetListener: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val query = request.queryString
        val id = URLDecoder.decode(query.substringAfter("id="), "utf-8")
        request.setAttribute("id", id)
        request.getRequestDispatcher("../response.jsp").forward(request, response)
    }
}
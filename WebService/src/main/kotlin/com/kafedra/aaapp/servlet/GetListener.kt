package com.kafedra.aaapp.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "GetListener", urlPatterns = ["echo/get"], loadOnStartup = 1)
class GetListener: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val query = request.queryString
        val id = query.substringAfter("id=")
        request.setAttribute("id", id)
        request.getRequestDispatcher("../response.jsp").forward(request, response)
    }
}
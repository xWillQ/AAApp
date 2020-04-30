package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import java.net.URLDecoder
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class GetListener: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val query = request.queryString
        val id = URLDecoder.decode(query.substringAfter("id="), "utf-8")
        request.setAttribute("id", id)
        request.getRequestDispatcher("../response.jsp").forward(request, response)
    }
}
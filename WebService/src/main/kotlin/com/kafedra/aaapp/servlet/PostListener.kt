package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import java.net.URLEncoder
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class PostListener: HttpServlet() {
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        var input = request.getParameter("input")
        if (input == null) input = "Hello"
        input = URLEncoder.encode(input, "utf-8")
        response.sendRedirect("/echo/get?id=$input")
    }
}
package com.kafedra.aaapp.servlet

import java.net.URLEncoder
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "PostListener", urlPatterns = ["echo/post"], loadOnStartup = 1)
class PostListener: HttpServlet() {
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        var input = request.getParameter("input")
        if (input == null) input = "Hello"
        input = URLEncoder.encode(input, "utf-8")
        response.sendRedirect("/echo/get?id=$input")
    }
}
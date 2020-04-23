package com.kafedra.aaapp.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "EchoListener", urlPatterns = ["echo/*"], loadOnStartup = 1)
class EchoListener: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.print("404 not found")
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.print("404 not found")
    }
}
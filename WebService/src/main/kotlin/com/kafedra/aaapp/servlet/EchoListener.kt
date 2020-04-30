package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class EchoListener: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.print("404 not found")
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.print("404 not found")
    }
}
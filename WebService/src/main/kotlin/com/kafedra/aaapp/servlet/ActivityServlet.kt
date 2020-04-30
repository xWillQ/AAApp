package com.kafedra.aaapp.servlet

import com.google.inject.Singleton
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class ActivityServlet: HttpServlet() {

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.print("Activity")
    }
}
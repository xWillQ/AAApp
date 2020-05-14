package com.kafedra.aaapp.servlet

import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.dao.UserDao
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.aaapp.di.injector.InjectLogger
import org.apache.logging.log4j.Logger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class UserServlet: HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger
    @Inject
    lateinit var gsonProvider: GSONProvider
    @Inject
    lateinit var dao: UserDao

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /ajax/user")

        var id = request.getParameter("id")?.toIntOrNull()
        if (id == null) {
            logger.info("id is not specified, returning all users")
            id = 0
        }
        else logger.info("id = $id")

        logger.info("Getting users from database")
        val userList = dao.getUser(id)

        logger.info("Converting users to json")
        val gson = gsonProvider.get()
        val json = gson.toJson(userList)

        response.contentType = "text/plain"
        response.writer.print(json)
    }
}
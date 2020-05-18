package com.kafedra.aaapp.servlet

import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.dao.AuthorityDao
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.aaapp.di.injector.InjectLogger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.logging.log4j.Logger

@Singleton
class AuthorityServlet : HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger
    @Inject
    lateinit var gsonProvider: GSONProvider
    @Inject
    lateinit var dao: AuthorityDao

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /ajax/authority")

        val id = request.getParameter("id")?.toIntOrNull()
        val userId = request.getParameter("userId")?.toIntOrNull()

        val authorityList = when {
            id != null -> {
                logger.info("id = $id")
                logger.info("Getting authorities from database")
                dao.getAuthority(id)
            }
            userId != null -> {
                logger.info("userId = $userId")
                logger.info("Getting authorities from database")
                dao.getAuthorityByUser(userId)
            }
            else -> {
                logger.info("id and userId are not specified, returning all authorities")
                logger.info("Getting authorities from database")
                dao.getAuthority(0)
            }
        }

        logger.info("Converting authorities to json")
        val gson = gsonProvider.get()
        val json = gson.toJson(authorityList)

        response.contentType = "text/plain"
        response.writer.print(json)
    }
}

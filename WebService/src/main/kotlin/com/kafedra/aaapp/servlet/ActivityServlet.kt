package com.kafedra.aaapp.servlet

import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.dao.ActivityDao
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.aaapp.di.injector.InjectLogger
import org.apache.logging.log4j.Logger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class ActivityServlet: HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger
    @Inject
    lateinit var gsonProvider: GSONProvider
    @Inject
    lateinit var dao: ActivityDao


    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /ajax/user")

        val id = request.getParameter("id")?.toIntOrNull()
        val authorityId = request.getParameter("authorityId")?.toIntOrNull()

        val activityList = when {
            id != null -> {
                logger.info("id = $id")
                logger.info("Getting activities from database")
                dao.getActivity(id)
            }
            authorityId != null -> {
                logger.info("authorityId = $authorityId")
                logger.info("Getting activities from database")
                dao.getActivityByAuthority(authorityId)
            }
            else -> {
                logger.info("id and authorityId are not specified, returning all activities")
                logger.info("Getting activities from database")
                dao.getActivity(0)
            }
        }

        logger.info("Converting activities to json")
        val gson = gsonProvider.get()
        val json = gson.toJson(activityList)

        response.contentType = "text/plain"
        response.writer.print(json)
    }
}
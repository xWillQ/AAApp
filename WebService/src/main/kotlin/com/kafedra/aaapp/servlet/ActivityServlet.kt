package com.kafedra.aaapp.servlet

import com.google.gson.reflect.TypeToken
import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.App
import com.kafedra.aaapp.dao.ActivityDao
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.aaapp.di.injector.InjectLogger
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.logging.log4j.Logger

@Singleton
class ActivityServlet : HttpServlet() {
    @InjectLogger
    lateinit var logger: Logger
    @Inject
    lateinit var gsonProvider: GSONProvider
    @Inject
    lateinit var dao: ActivityDao

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling POST request")
        logger.info("Receiving json")
        val json = request.reader.readLine()
        logger.info("json = \"$json\"")
        logger.info("Deserializing json")
        val type = object : TypeToken<Map<String, String>>() {}.type
        val gson = gsonProvider.get()
        val map = gson.fromJson<Map<String, String>>(json, type)

        logger.info("Converting argument map to array")
        val argList = mutableListOf<String>()
        for (pair in map) {
            if (pair.value != "") {
                argList.add("-${pair.key}")
                argList.add(pair.value)
            }
        }

        logger.info("Running application")
        val app = App()
        val exitCode = app.run(argList.toTypedArray())
        logger.info("Application exited with code ${exitCode.code} (${exitCode.name})")
        logger.info("Sending exit code as a response")
        response.writer.write("${exitCode.code}")
    }

    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handling /ajax/activity")

        if (request.method == "POST") {
            this.doPost(request, response)
            return
        }

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

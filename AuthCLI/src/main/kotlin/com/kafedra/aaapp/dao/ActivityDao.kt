package com.kafedra.aaapp.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.aaapp.domain.Activity
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActivityDao {
    @Inject lateinit var sessionProvider: HibernateProvider
    val logger: Logger = LogManager.getLogger()

    fun addActivity(activity: Activity) {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        logger.info("Starting transaction")
        session.beginTransaction()
        logger.info("Adding activity to the DB")
        session.save(activity)
        logger.info("Committing transaction")
        session.transaction.commit()

        logger.info("Closing hibernate session")
        session.close()
    }

    fun getActivity(id: Int): List<Activity> {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        val activityList = if (id != 0) {
            logger.info("Querying activity with id = $id")
            val activity = session.get(Activity::class.java, id)
            if (activity != null) {
                logger.info("Activity exists, returning requested activity")
                listOf(activity)
            } else {
                logger.info("Activity doesn't exists, returning empty list")
                listOf()
            }
        } else {
            logger.info("Id = 0, querying all activities")
            val query = session.createQuery("FROM Activity", Activity::class.java)
            query.resultList
        }

        logger.info("Closing hibernate session")
        session.close()

        return activityList
    }

    fun getActivityByAuthority(authorityId: Int): List<Activity> {
        logger.info("Opening hibernate session")
        val session = sessionProvider.get().openSession()

        logger.info("Querying activities with authorityId = $authorityId")
        val query = session.createQuery(
                "FROM Activity WHERE authorityId = $authorityId",
                Activity::class.java
        )

        val activitiesList = query.resultList
        logger.info("Received ${activitiesList.size} activities")

        logger.info("Closing hibernate session")
        session.close()

        return activitiesList
    }
}

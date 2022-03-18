package xyz.teamgravity.calendarintegration.core.util

import java.util.*

object Time {

    const val FULL_TIME_FORMATTER = "fullTimeFormatter"
    const val ONLY_DATE_FORMATTER = "onlyDateFormatter"

    /**
     * return minimum day for comparing time in millis for sql
     */
    fun minDay(time: Long): Long {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        c.add(Calendar.DATE, -1)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        return c.timeInMillis
    }

    /**
     * return maximum day for comparing time in millis for sql
     */
    fun maxDay(time: Long): Long {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        c.add(Calendar.DATE, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        return c.timeInMillis
    }
}
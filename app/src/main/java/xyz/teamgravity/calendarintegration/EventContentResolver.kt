package xyz.teamgravity.calendarintegration

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import java.util.*

@SuppressLint("Range")
class EventContentResolver(
    context: Context
) {

    companion object {
        val URI: Uri = Uri.parse("content://com.android.calendar/events")
        const val PERMISSION = Manifest.permission.READ_CALENDAR
    }

    private val resolver = context.contentResolver

    fun getEvents(): HashSet<EventModel> {
        val events = hashSetOf<EventModel>()

        val cursor = resolver.query(
            URI,
            arrayOf(
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
            ), null, null, null
        )

        cursor?.let {
            try {
                if (it.count > 0) {
                    while (it.moveToNext()) {
                        val title = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE)) ?: ""
                        val description = it.getString(it.getColumnIndex(CalendarContract.Events.DESCRIPTION)) ?: ""
                        val startDate = it.getLong(it.getColumnIndex(CalendarContract.Events.DTSTART))
                        val endDate = it.getLong(it.getColumnIndex(CalendarContract.Events.DTEND))

                        events.add(
                            EventModel(
                                title = title,
                                description = description,
                                startDate = startDate,
                                endDate = endDate
                            )
                        )
                    }
                }
            } catch (e: AssertionError) {
                raheem(e.message)
                cursor.close()
            }
        }

        cursor?.close()
        return events
    }

    fun getEventsByDate(date: Date) {
        val events = hashSetOf<EventModel>()

        val cursor = resolver.query(
            URI,
            arrayOf(
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
            ),
            CalendarContract.Events.DTSTART + " > ? AND " + CalendarContract.Events.DTSTART + " < ?",
            arrayOf(Time.minDay(date.time).toString(), Time.maxDay(date.time).toString()),
            null
        )

        cursor?.let {
            try {
                if (it.count > 0) {
                    while (it.moveToNext()) {
                        val title = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE)) ?: ""
                        val description = it.getString(it.getColumnIndex(CalendarContract.Events.DESCRIPTION)) ?: ""
                        val startDate = it.getLong(it.getColumnIndex(CalendarContract.Events.DTSTART))
                        val endDate = it.getLong(it.getColumnIndex(CalendarContract.Events.DTEND))

                        events.add(
                            EventModel(
                                title = title,
                                description = description,
                                startDate = startDate,
                                endDate = endDate
                            )
                        )
                    }
                }
            } catch (e: AssertionError) {
                raheem(e.message)
                cursor.close()
            }
        }

        cursor?.close()
        return events
    }
}

fun raheem(message: Any?) = println("raheem: $message")
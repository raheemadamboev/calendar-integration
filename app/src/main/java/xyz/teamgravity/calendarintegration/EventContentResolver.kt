package xyz.teamgravity.calendarintegration

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract

class EventContentResolver(
    context: Context
) {

    companion object {
        val URI: Uri = Uri.parse("content://com.android.calendar/events")
    }

    private val resolver = context.contentResolver

    @SuppressLint("Range")
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
}

fun raheem(message: Any?) = println("raheem: $message")
package xyz.teamgravity.calendarintegration.core.resolver

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.teamgravity.calendarintegration.core.extension.raheem
import xyz.teamgravity.calendarintegration.core.util.Time
import xyz.teamgravity.calendarintegration.data.model.EventModel

@SuppressLint("Range")
class EventContentResolver(context: Context) {

    companion object {
        val URI: Uri = Uri.parse("content://com.android.calendar/events")
        const val PERMISSION = Manifest.permission.READ_CALENDAR
    }

    private val resolver = context.contentResolver

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getEvents(): Flow<HashSet<EventModel>> = flow {
        val events = hashSetOf<EventModel>()

        val cursor = resolver.query(
            URI,
            arrayOf(
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
            ),
            null,
            null,
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
                cursor.close()
                raheem(e.message)
            }
        }

        cursor?.close()
        emit(events)
    }

    fun getDateEvents(time: Long): Flow<HashSet<EventModel>> = flow {
        val events = hashSetOf<EventModel>()

        val cursor = resolver.query(
            URI,
            arrayOf(
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
            ),
            CalendarContract.Events.DTSTART + " > ? AND " + CalendarContract.Events.DTSTART + " < ?", // condition
            arrayOf(Time.minDay(time).toString(), Time.maxDay(time).toString()), // arguments
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
                cursor.close()
                raheem(e.message)
            }
        }

        cursor?.close()
        emit(events)
    }
}
package xyz.teamgravity.calendarintegration.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import xyz.teamgravity.calendarintegration.data.model.EventModel
import xyz.teamgravity.calendarintegration.data.resolver.EventContentResolver

class CalendarIntegrationRepository(
    private val resolver: EventContentResolver
) {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertEvent(event: EventModel) {
        withContext(Dispatchers.IO) {
            resolver.insertEvent(event)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getEvents(): Flow<HashSet<EventModel>> {
        return resolver.getEvents()
    }

    fun getDateEvents(time: Long): Flow<HashSet<EventModel>> {
        return resolver.getDateEvents(time)
    }
}
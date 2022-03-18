package xyz.teamgravity.calendarintegration.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.calendarintegration.core.resolver.EventContentResolver
import xyz.teamgravity.calendarintegration.data.model.EventModel

class CalendarIntegrationRepository(
    private val resolver: EventContentResolver
) {

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
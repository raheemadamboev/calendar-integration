package xyz.teamgravity.calendarintegration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.teamgravity.calendarintegration.data.repository.CalendarIntegrationRepository
import javax.inject.Inject

@HiltViewModel
class EventCalendarViewModel @Inject constructor(
    repository: CalendarIntegrationRepository
) : ViewModel() {

    val events = repository.getEvents()
}
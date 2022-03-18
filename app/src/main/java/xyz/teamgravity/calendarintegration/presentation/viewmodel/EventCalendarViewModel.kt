package xyz.teamgravity.calendarintegration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.calendarintegration.core.util.FlowWrapper
import xyz.teamgravity.calendarintegration.data.model.EventModel
import xyz.teamgravity.calendarintegration.data.repository.CalendarIntegrationRepository
import javax.inject.Inject

@HiltViewModel
class EventCalendarViewModel @Inject constructor(
    repository: CalendarIntegrationRepository
) : ViewModel() {

    val events = repository.getEvents()
}
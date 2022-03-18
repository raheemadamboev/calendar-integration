package xyz.teamgravity.calendarintegration.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.data.repository.CalendarIntegrationRepository
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    handle: SavedStateHandle,
    repository: CalendarIntegrationRepository
) : ViewModel() {

    val events = repository.getDateEvents(time = handle.get<Long>(Extra.SELECTED_TIME)!!)
}
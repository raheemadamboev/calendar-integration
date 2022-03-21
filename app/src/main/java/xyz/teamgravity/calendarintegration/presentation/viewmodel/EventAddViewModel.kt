package xyz.teamgravity.calendarintegration.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.data.model.EventModel
import xyz.teamgravity.calendarintegration.data.repository.CalendarIntegrationRepository
import javax.inject.Inject

@HiltViewModel
class EventAddViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: CalendarIntegrationRepository
) : ViewModel() {

    private val _event = Channel<EventAddEvent> { }
    val event: Flow<EventAddEvent> = _event.receiveAsFlow()

    fun onAddEvent(title: String, description: String) {
        viewModelScope.launch {
            repository.insertEvent(
                EventModel(
                    title = title,
                    description = description,
                    startDate = handle.get<Long>(Extra.SELECTED_TIME)!!,
                    endDate = handle.get<Long>(Extra.SELECTED_TIME)!! + 60_000L // TODO statically one minute added, in real app ask it from user!
                )
            )
            _event.send(EventAddEvent.EventAdded)
        }
    }

    sealed class EventAddEvent {
        object EventAdded : EventAddEvent()
    }
}
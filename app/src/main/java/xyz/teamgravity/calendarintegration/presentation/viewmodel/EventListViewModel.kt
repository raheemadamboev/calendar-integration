package xyz.teamgravity.calendarintegration.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.core.util.FlowWrapper
import xyz.teamgravity.calendarintegration.data.model.EventModel
import xyz.teamgravity.calendarintegration.data.repository.CalendarIntegrationRepository
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: CalendarIntegrationRepository
) : ViewModel() {

    private val _events = MutableStateFlow(FlowWrapper.default(emptyList<EventModel>()))
    val events: StateFlow<FlowWrapper<List<EventModel>>> = _events.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getDateEvents(time = handle.get<Long>(Extra.SELECTED_TIME)!!).collectLatest { data ->
                _events.emit(FlowWrapper.ready(data.toList()))
            }
        }
    }
}
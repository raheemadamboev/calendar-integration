package xyz.teamgravity.calendarintegration.data.model

data class EventModel(
    val title: String,
    val description: String,
    val startDate: Long,
    val endDate: Long
)
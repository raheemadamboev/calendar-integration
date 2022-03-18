package xyz.teamgravity.calendarintegration.core.util

data class FlowWrapper<T>(
    val data: T,
    val ready: Boolean
) {
    companion object {
        fun <T> default(data: T) = FlowWrapper(data = data, ready = false)
        fun <T> ready(data: T) = FlowWrapper(data = data, ready = true)
    }
}
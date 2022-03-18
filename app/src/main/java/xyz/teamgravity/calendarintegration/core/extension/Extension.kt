package xyz.teamgravity.calendarintegration.core.extension

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow


fun raheem(message: Any?) = println("raheem: $message")

@OptIn(ExperimentalCoroutinesApi::class)
fun ContentResolver.register(uri: Uri) = callbackFlow<Boolean> {
    val observer = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            trySend(selfChange)
        }
    }
    registerContentObserver(uri, false, observer)

    invokeOnClose {
        unregisterContentObserver(observer)
    }
}
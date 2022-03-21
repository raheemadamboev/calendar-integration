package xyz.teamgravity.calendarintegration.core.extension

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import xyz.teamgravity.calendarintegration.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun TextInputLayout.clearErrorFocus() {
    error = null
    clearFocus()
}

fun TextInputLayout.text(): String {
    return editText?.text.toString().trim()
}

fun TextInputLayout.setText(text: String) {
    editText?.setText(text)
}

fun TextInputLayout.fieldError() {
    error = context.getString(R.string.error_field)
    requestFocus()
}

fun TextInputLayout.error(error: String) {
    this.error = error
    requestFocus()
}
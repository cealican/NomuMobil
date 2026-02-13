package alican.app.nomu.util

import androidx.compose.runtime.MutableState

fun Boolean.toggle(): Boolean {
    return !this
}


fun MutableState<Boolean>.toggle() {
    value = !value
}
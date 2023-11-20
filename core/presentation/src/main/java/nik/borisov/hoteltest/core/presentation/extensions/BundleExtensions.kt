package nik.borisov.hoteltest.core.presentation.extensions

import android.os.Bundle

const val LABEL_SCREEN = "label_screen"

fun Bundle.getScreenLabel(): String? {
    return this.getString(LABEL_SCREEN, null)
}
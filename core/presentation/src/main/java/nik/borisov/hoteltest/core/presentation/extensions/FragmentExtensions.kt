package nik.borisov.hoteltest.core.presentation.extensions

import android.os.Build
import androidx.fragment.app.Fragment
import nik.borisov.hoteltest.core.presentation.BaseScreenArgs

const val ARG_SCREEN = "screen"

inline fun <reified T : BaseScreenArgs> Fragment.args(): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requireArguments().getParcelable(ARG_SCREEN, T::class.java)
            ?: throw IllegalStateException("Screen args don't exist")
    } else {
        @Suppress("DEPRECATION")
        requireArguments().getParcelable(ARG_SCREEN) as? T
            ?: throw IllegalStateException("Screen args don't exist")
    }
}
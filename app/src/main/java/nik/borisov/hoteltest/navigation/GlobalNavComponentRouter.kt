package nik.borisov.hoteltest.navigation

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import nik.borisov.hoteltest.core.presentation.ActivityRequired
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalNavComponentRouter @Inject constructor() : ActivityRequired {

    private var activity: FragmentActivity? = null
    private var started = false
    private var completelyDestroyed = true
    private val commands = mutableListOf<() -> Unit>()

    override fun onCreated(activity: FragmentActivity) {
        this.completelyDestroyed = false
        this.activity = activity
    }

    override fun onStarted() {
        started = true
        commands.forEach { it() }
        commands.clear()
    }

    override fun onStopped() {
        started = false
    }

    override fun onDestroyed() {
        if (activity?.isFinishing == true) {
            commands.clear()
            completelyDestroyed = true
        }
    }

    fun pop() = invoke {
        requireRealRouter().pop()
    }

    fun restart() = invoke {
        requireRealRouter().restart()
    }

    fun launch(@IdRes destinationId: Int, args: Parcelable? = null, label: String? = null) =
        invoke {
            requireRealRouter().launch(destinationId, args, label)
        }

    private fun invoke(command: () -> Unit) {
        if (started) {
            command()
        } else if (!completelyDestroyed) {
            commands.add(command)
        }
    }

    private fun requireRealRouter(): NavComponentRouter {
        return (activity as RouterHolder).requireRouter()
    }
}
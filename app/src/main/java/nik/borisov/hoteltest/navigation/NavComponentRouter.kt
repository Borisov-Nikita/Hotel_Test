package nik.borisov.hoteltest.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import nik.borisov.hoteltest.core.presentation.extensions.ARG_SCREEN
import nik.borisov.hoteltest.core.presentation.extensions.LABEL_SCREEN
import nik.borisov.hoteltest.core.presentation.extensions.getScreenLabel

class NavComponentRouter @AssistedInject constructor(
    @Assisted @IdRes private val fragmentContainerId: Int,
    private val activity: FragmentActivity
) {

    private var navController: NavController? = null

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            val appCompatActivity =
                activity as? AppCompatActivity ?: return@OnDestinationChangedListener
            val title = prepareTitle(destination.label, arguments)
            if (title.isNotBlank()) {
                appCompatActivity.supportActionBar?.title = title
            }
            appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(
                !isStartDestination(
                    destination
                )
            )
        }

    fun onCreate() {
        val navHost =
            activity.supportFragmentManager.findFragmentById(fragmentContainerId) as NavHostFragment
        navController = navHost.navController
        navController?.addOnDestinationChangedListener(destinationListener)
    }

    fun onDestroy() {
        navController?.removeOnDestinationChangedListener(destinationListener)
        navController = null
    }

    fun onNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }

    fun launch(@IdRes destinationId: Int, args: Parcelable? = null, label: String? = null) {
        if (args == null && label == null) {
            navController?.navigate(resId = destinationId)
        } else {
            navController?.navigate(
                resId = destinationId,
                args = Bundle().apply {
                    if (args != null) putParcelable(ARG_SCREEN, args)
                    if (label != null) putString(LABEL_SCREEN, label)
                }
            )
        }
    }

    fun pop() {
        activity.onBackPressedDispatcher.onBackPressed()
    }

    fun restart() {
        navController?.popBackStack(navController?.graph?.startDestinationId!!, false)
    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {
        if (label == null) return ""
        val titleFromArgs = arguments?.getScreenLabel()
        return titleFromArgs ?: label.toString()
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        return navController?.graph?.startDestinationId == destination.id
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @IdRes fragmentContainerId: Int,
        ): NavComponentRouter
    }
}
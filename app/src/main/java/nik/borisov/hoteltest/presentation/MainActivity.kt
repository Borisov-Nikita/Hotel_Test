package nik.borisov.hoteltest.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import dagger.hilt.android.AndroidEntryPoint
import nik.borisov.hoteltest.R
import nik.borisov.hoteltest.core.presentation.ActivityRequired
import nik.borisov.hoteltest.databinding.ActivityMainBinding
import nik.borisov.hoteltest.navigation.NavComponentRouter
import nik.borisov.hoteltest.navigation.RouterHolder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RouterHolder {

    @Inject
    lateinit var activityRequired: ActivityRequired

    @Inject
    lateinit var navComponentRouterFactory: NavComponentRouter.Factory

    private val navComponentRouter by lazy(LazyThreadSafetyMode.NONE) {
        navComponentRouterFactory.create(R.id.fragmentContainer)
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(
                bottom = insets.systemWindowInsetBottom,
                top = insets.systemWindowInsetTop
            )
            insets
        }


        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT

        navComponentRouter.onCreate()
        activityRequired.onCreated(this)
    }

    override fun onStart() {
        super.onStart()
        activityRequired.onStarted()
    }

    override fun onStop() {
        super.onStop()
        activityRequired.onStarted()
    }

    override fun onDestroy() {
        navComponentRouter.onDestroy()
        super.onDestroy()
        activityRequired.onDestroyed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navComponentRouter.onNavigateUp() || super.onSupportNavigateUp()
    }

    override fun requireRouter(): NavComponentRouter {
        return navComponentRouter
    }
}
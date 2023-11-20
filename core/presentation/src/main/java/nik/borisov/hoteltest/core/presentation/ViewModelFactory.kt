package nik.borisov.hoteltest.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<VM : ViewModel>(
    private val viewModelCreator: () -> VM
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return viewModelCreator() as T
    }
}

inline fun <reified VM : ViewModel> Fragment.viewModelCreator(
    noinline creator: () -> VM
): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}
package nik.borisov.hoteltest.features.paid.presentation.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nik.borisov.hoteltest.features.paid.presentation.PaidRouter
import javax.inject.Inject

@HiltViewModel
class PaidViewModel @Inject constructor(
    private val paidRouter: PaidRouter
) : ViewModel() {

    fun popBack() {
        paidRouter.popBack()
    }
}
package nik.borisov.hoteltest.union.booking.adapters

import nik.borisov.hoteltest.R
import nik.borisov.hoteltest.features.booking.presentation.BookingRouter
import nik.borisov.hoteltest.features.paid.presentation.screens.PaidFragment
import nik.borisov.hoteltest.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterBookingRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : BookingRouter {

    override fun launchPaidScreen(orderNumber: Int) {
        globalNavComponentRouter.launch(
            R.id.paidFragment,
            PaidFragment.PaidScreenArgs(orderNumber)
        )
    }
}
package nik.borisov.hoteltest.union.rooms.adapters

import nik.borisov.hoteltest.R
import nik.borisov.hoteltest.features.booking.presentation.screens.BookingFragment
import nik.borisov.hoteltest.features.rooms.presentation.RoomsRouter
import nik.borisov.hoteltest.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterRoomsRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : RoomsRouter {

    override fun launchBookingScreen(roomId: Long) {
        globalNavComponentRouter.launch(
            R.id.bookingFragment,
            BookingFragment.BookingScreenArgs(roomId)
        )
    }
}
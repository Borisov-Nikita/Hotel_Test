package nik.borisov.hoteltest.union.hotel.adapters

import nik.borisov.hoteltest.R
import nik.borisov.hoteltest.features.hotel.presentation.HotelRouter
import nik.borisov.hoteltest.features.rooms.presentation.screens.RoomsFragment
import nik.borisov.hoteltest.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterHotelRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : HotelRouter {

    override fun launchRoomsScreen(hotelId: Long, hotelName: String) {
        globalNavComponentRouter.launch(
            R.id.roomsFragment,
            RoomsFragment.RoomsScreenArgs(hotelId),
            hotelName
        )
    }
}
package nik.borisov.hoteltest.union.booking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import nik.borisov.hoteltest.features.booking.presentation.BookingRouter
import nik.borisov.hoteltest.union.booking.adapters.AdapterBookingRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface BookingRouterModule {

    @Binds
    fun bindBookingRouter(bookingRouter: AdapterBookingRouter): BookingRouter
}
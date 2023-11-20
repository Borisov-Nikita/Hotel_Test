package nik.borisov.hoteltest.union.hotel.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import nik.borisov.hoteltest.features.hotel.presentation.HotelRouter
import nik.borisov.hoteltest.union.hotel.adapters.AdapterHotelRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface HotelRouterModule {

    @Binds
    fun bindHotelRouter(hotelRouter: AdapterHotelRouter): HotelRouter
}
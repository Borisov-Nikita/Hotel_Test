package nik.borisov.hoteltest.union.booking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.features.booking.domain.repositories.BookingRepository
import nik.borisov.hoteltest.union.booking.adapters.AdapterBookingRepository

@Module
@InstallIn(SingletonComponent::class)
interface BookingRepositoryModule {

    @Binds
    fun bindBookingRepository(bookingRepository: AdapterBookingRepository): BookingRepository
}
package nik.borisov.hoteltest.union.hotel.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.features.hotel.domain.repositories.HotelRepository
import nik.borisov.hoteltest.union.hotel.adapters.AdapterHotelRepository

@Module
@InstallIn(SingletonComponent::class)
interface HotelRepositoriesModule {

    @Binds
    fun bindHotelRepository(hotelRepository: AdapterHotelRepository): HotelRepository
}
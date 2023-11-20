package nik.borisov.hoteltest.data.sources.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.data.sources.remote.ApiFactory
import nik.borisov.hoteltest.data.sources.remote.HotelApiService

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    fun provideHotelApiService(): HotelApiService {
        return ApiFactory.hotelApiService
    }
}
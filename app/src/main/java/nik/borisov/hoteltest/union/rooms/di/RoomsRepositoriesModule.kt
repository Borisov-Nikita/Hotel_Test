package nik.borisov.hoteltest.union.rooms.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.features.rooms.domain.repositiries.RoomsRepository
import nik.borisov.hoteltest.union.rooms.adapters.AdapterRoomsRepository

@Module
@InstallIn(SingletonComponent::class)
interface RoomsRepositoriesModule {

    @Binds
    fun bindRoomsRepository(roomsRepository: AdapterRoomsRepository): RoomsRepository
}
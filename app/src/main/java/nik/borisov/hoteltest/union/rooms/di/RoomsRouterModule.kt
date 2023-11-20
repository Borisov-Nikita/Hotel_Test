package nik.borisov.hoteltest.union.rooms.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import nik.borisov.hoteltest.features.rooms.presentation.RoomsRouter
import nik.borisov.hoteltest.union.rooms.adapters.AdapterRoomsRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RoomsRouterModule {

    @Binds
    fun bindRoomsRouter(roomsRouter: AdapterRoomsRouter): RoomsRouter
}
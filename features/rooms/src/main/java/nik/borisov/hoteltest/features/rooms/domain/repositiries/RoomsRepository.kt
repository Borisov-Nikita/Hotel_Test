package nik.borisov.hoteltest.features.rooms.domain.repositiries

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.rooms.domain.entities.Room
import nik.borisov.hoteltest.features.rooms.domain.entities.RoomSearchFilters

interface RoomsRepository {

    fun getRooms(filter: RoomSearchFilters): Flow<StateResult<List<Room>>>
}
package nik.borisov.hoteltest.features.rooms.domain

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.rooms.domain.entities.Room
import nik.borisov.hoteltest.features.rooms.domain.entities.RoomSearchFilters
import nik.borisov.hoteltest.features.rooms.domain.repositiries.RoomsRepository
import javax.inject.Inject

class RoomsUseCase @Inject constructor(
    private val roomsRepository: RoomsRepository
) {

    fun getRooms(filter: RoomSearchFilters): Flow<StateResult<List<Room>>> {
        return roomsRepository.getRooms(filter)
    }
}
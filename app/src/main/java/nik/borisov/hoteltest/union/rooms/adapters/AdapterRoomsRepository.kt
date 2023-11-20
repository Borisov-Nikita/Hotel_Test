package nik.borisov.hoteltest.union.rooms.adapters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.repositories.DataRepository
import nik.borisov.hoteltest.features.rooms.domain.entities.Room
import nik.borisov.hoteltest.features.rooms.domain.entities.RoomSearchFilters
import nik.borisov.hoteltest.features.rooms.domain.repositiries.RoomsRepository
import nik.borisov.hoteltest.union.rooms.mappers.RoomsMapper
import javax.inject.Inject

class AdapterRoomsRepository @Inject constructor(
    private val dataRepository: DataRepository,
    private val mapper: RoomsMapper
) : RoomsRepository {

    override fun getRooms(filter: RoomSearchFilters): Flow<StateResult<List<Room>>> {
        return dataRepository.provideRooms(
            mapper.mapEntitySearchFiltersToData(filter)
        ).map { stateResult ->
            stateResult.map { dto ->
                mapper.mapRoomDtoListToEntity(dto)
            }
        }
    }
}
package nik.borisov.hoteltest.union.rooms.mappers

import nik.borisov.hoteltest.data.sources.remote.model.RoomDto
import nik.borisov.hoteltest.data.sources.remote.request.RoomSearchDataFilters
import nik.borisov.hoteltest.features.rooms.domain.entities.Room
import nik.borisov.hoteltest.features.rooms.domain.entities.RoomSearchFilters
import javax.inject.Inject

class RoomsMapper @Inject constructor() {

    fun mapRoomDtoListToEntity(dto: List<RoomDto>): List<Room> {
        return dto.map {
            Room(
                id = it.id,
                name = it.name,
                price = it.price,
                pricePer = it.pricePer,
                peculiarities = it.peculiarities,
                imageUrls = it.imageUrls
            )
        }
    }

    fun mapEntitySearchFiltersToData(filters: RoomSearchFilters): RoomSearchDataFilters {
        return RoomSearchDataFilters(
            hotelId = filters.hotelId
        )
    }
}
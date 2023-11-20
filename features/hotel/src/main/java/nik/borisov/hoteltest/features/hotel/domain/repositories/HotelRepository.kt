package nik.borisov.hoteltest.features.hotel.domain.repositories

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.hotel.domain.entities.Hotel

interface HotelRepository {

    fun getHotel(hotelId: Long): Flow<StateResult<Hotel>>
}
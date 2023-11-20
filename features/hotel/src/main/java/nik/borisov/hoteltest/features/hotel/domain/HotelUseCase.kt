package nik.borisov.hoteltest.features.hotel.domain

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.hotel.domain.entities.Hotel
import nik.borisov.hoteltest.features.hotel.domain.repositories.HotelRepository
import javax.inject.Inject

class HotelUseCase @Inject constructor(
    private val hotelRepository: HotelRepository
) {

    fun getHotel(hotelId: Long): Flow<StateResult<Hotel>> {
        return hotelRepository.getHotel(hotelId)
    }
}
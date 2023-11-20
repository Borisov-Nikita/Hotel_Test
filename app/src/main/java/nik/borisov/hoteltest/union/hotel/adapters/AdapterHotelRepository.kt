package nik.borisov.hoteltest.union.hotel.adapters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.repositories.DataRepository
import nik.borisov.hoteltest.features.hotel.domain.entities.Hotel
import nik.borisov.hoteltest.features.hotel.domain.repositories.HotelRepository
import nik.borisov.hoteltest.union.hotel.mappers.HotelMapper
import javax.inject.Inject

class AdapterHotelRepository @Inject constructor(
    private val dataRepository: DataRepository,
    private val mapper: HotelMapper
) : HotelRepository {

    override fun getHotel(hotelId: Long): Flow<StateResult<Hotel>> {
        return dataRepository.provideHotel(hotelId).map { stateResult ->
            stateResult.map { dto ->
                mapper.mapDtoToEntity(dto)
            }
        }
    }
}
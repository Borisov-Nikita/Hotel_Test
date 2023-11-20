package nik.borisov.hoteltest.union.booking.adapters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.repositories.DataRepository
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo
import nik.borisov.hoteltest.features.booking.domain.entities.OrderInfo
import nik.borisov.hoteltest.features.booking.domain.repositories.BookingRepository
import nik.borisov.hoteltest.union.booking.mappers.BookingMapper
import javax.inject.Inject

class AdapterBookingRepository @Inject constructor(
    private val dataRepository: DataRepository,
    private val mapper: BookingMapper
) : BookingRepository {

    override fun getBookingInfo(roomId: Long): Flow<StateResult<BookingInfo>> {
        return dataRepository.provideBookingInfo(roomId).map { stateResult ->
            stateResult.map { dto ->
                mapper.mapBookingInfoDtoToEntity(dto)
            }
        }
    }

    override fun doBookingOrder(orderInfo: OrderInfo): Flow<StateResult<Int>> {
        return dataRepository.doBookingOrder(mapper.mapOrderInfoEntityToData(orderInfo))
    }
}
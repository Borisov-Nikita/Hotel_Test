package nik.borisov.hoteltest.features.booking.domain.repositories

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo
import nik.borisov.hoteltest.features.booking.domain.entities.OrderInfo

interface BookingRepository {

    fun getBookingInfo(roomId: Long): Flow<StateResult<BookingInfo>>

    fun doBookingOrder(orderInfo: OrderInfo): Flow<StateResult<Int>>
}
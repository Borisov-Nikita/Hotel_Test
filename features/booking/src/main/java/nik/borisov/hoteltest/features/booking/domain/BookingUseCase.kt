package nik.borisov.hoteltest.features.booking.domain

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo
import nik.borisov.hoteltest.features.booking.domain.entities.OrderInfo
import nik.borisov.hoteltest.features.booking.domain.repositories.BookingRepository
import javax.inject.Inject

class BookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {

    fun getBookingInfo(roomId: Long): Flow<StateResult<BookingInfo>> {
        return bookingRepository.getBookingInfo(roomId)
    }

    fun doBookingOrder(orderInfo: OrderInfo): Flow<StateResult<Int>> {
        return bookingRepository.doBookingOrder(orderInfo)
    }
}
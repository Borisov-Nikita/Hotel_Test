package nik.borisov.hoteltest.data.repositories

import kotlinx.coroutines.flow.Flow
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.sources.remote.model.BookingInfoDto
import nik.borisov.hoteltest.data.sources.remote.model.HotelDto
import nik.borisov.hoteltest.data.sources.remote.model.RoomDto
import nik.borisov.hoteltest.data.sources.remote.request.OrderDataInfo
import nik.borisov.hoteltest.data.sources.remote.request.RoomSearchDataFilters

interface DataRepository {

    fun provideHotel(hotelId: Long): Flow<StateResult<HotelDto>>

    fun provideRooms(filters: RoomSearchDataFilters): Flow<StateResult<List<RoomDto>>>

    fun provideBookingInfo(roomId: Long): Flow<StateResult<BookingInfoDto>>

    fun doBookingOrder(orderInfo: OrderDataInfo): Flow<StateResult<Int>>
}
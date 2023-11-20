package nik.borisov.hoteltest.data.sources

import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.sources.remote.model.BookingInfoDto
import nik.borisov.hoteltest.data.sources.remote.model.HotelDto
import nik.borisov.hoteltest.data.sources.remote.model.RoomDto
import nik.borisov.hoteltest.data.sources.remote.request.OrderDataInfo
import nik.borisov.hoteltest.data.sources.remote.request.RoomSearchDataFilters

interface DataSource {

    suspend fun downloadHotel(hotelId: Long): StateResult<HotelDto>

    suspend fun downloadRooms(filters: RoomSearchDataFilters): StateResult<List<RoomDto>>

    suspend fun downloadBookingInfo(roomId: Long): StateResult<BookingInfoDto>

    fun doBookingOrder(orderInfo: OrderDataInfo): StateResult<Int>
}
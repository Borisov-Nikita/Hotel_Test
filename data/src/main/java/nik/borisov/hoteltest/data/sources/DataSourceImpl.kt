package nik.borisov.hoteltest.data.sources

import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.sources.fake.OrderNumberFaker
import nik.borisov.hoteltest.data.sources.remote.HotelApiService
import nik.borisov.hoteltest.data.sources.remote.model.BookingInfoDto
import nik.borisov.hoteltest.data.sources.remote.model.HotelDto
import nik.borisov.hoteltest.data.sources.remote.model.RoomDto
import nik.borisov.hoteltest.data.sources.remote.request.OrderDataInfo
import nik.borisov.hoteltest.data.sources.remote.request.RoomSearchDataFilters
import nik.borisov.hoteltest.data.sources.remote.safeAsResult
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val hotelApiService: HotelApiService,
    private val orderNumberFaker: OrderNumberFaker
) : DataSource {

    override suspend fun downloadHotel(hotelId: Long): StateResult<HotelDto> {
        return hotelApiService.downloadHotel().safeAsResult { it }
    }

    override suspend fun downloadRooms(filters: RoomSearchDataFilters): StateResult<List<RoomDto>> {
        return hotelApiService.downloadRooms().safeAsResult { response ->
            mutableListOf<RoomDto>().apply {
                response.rooms.forEach { dto ->
                    add(dto)
                }
            }
        }
    }

    override suspend fun downloadBookingInfo(roomId: Long): StateResult<BookingInfoDto> {
        return hotelApiService.downloadBookingInfo().safeAsResult { it }
    }

    override fun doBookingOrder(orderInfo: OrderDataInfo): StateResult<Int> {
        return StateResult.Success(orderNumberFaker.getFakeOrderNumber())
    }
}
package nik.borisov.hoteltest.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.data.sources.DataSource
import nik.borisov.hoteltest.data.sources.remote.model.BookingInfoDto
import nik.borisov.hoteltest.data.sources.remote.model.HotelDto
import nik.borisov.hoteltest.data.sources.remote.model.RoomDto
import nik.borisov.hoteltest.data.sources.remote.request.OrderDataInfo
import nik.borisov.hoteltest.data.sources.remote.request.RoomSearchDataFilters
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val dataSource: DataSource
) : DataRepository {

    private val hotelFlow = MutableStateFlow<StateResult<HotelDto>>(StateResult.Pending)
    private val roomsFlow = MutableStateFlow<StateResult<List<RoomDto>>>(StateResult.Pending)
    private val bookingInfoFlow = MutableStateFlow<StateResult<BookingInfoDto>>(StateResult.Pending)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun provideHotel(hotelId: Long): Flow<StateResult<HotelDto>> {
        coroutineScope.launch {
            hotelFlow.emit(StateResult.Pending)
            hotelFlow.emit(dataSource.downloadHotel(hotelId))
        }
        return hotelFlow.asSharedFlow()
    }

    override fun provideRooms(filters: RoomSearchDataFilters): Flow<StateResult<List<RoomDto>>> {
        coroutineScope.launch {
            roomsFlow.emit(StateResult.Pending)
            roomsFlow.emit(dataSource.downloadRooms(filters))
        }
        return roomsFlow.asSharedFlow()
    }

    override fun provideBookingInfo(roomId: Long): Flow<StateResult<BookingInfoDto>> {
        coroutineScope.launch {
            bookingInfoFlow.emit(StateResult.Pending)
            bookingInfoFlow.emit(dataSource.downloadBookingInfo(roomId))
        }
        return bookingInfoFlow.asSharedFlow()
    }

    override fun doBookingOrder(orderInfo: OrderDataInfo): Flow<StateResult<Int>> {
        return flowOf(dataSource.doBookingOrder(orderInfo))
    }
}
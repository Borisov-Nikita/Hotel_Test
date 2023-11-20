package nik.borisov.hoteltest.data.sources.remote

import nik.borisov.hoteltest.data.sources.remote.model.BookingInfoDto
import nik.borisov.hoteltest.data.sources.remote.model.HotelDto
import nik.borisov.hoteltest.data.sources.remote.model.RoomsListDto
import retrofit2.Response
import retrofit2.http.GET

interface HotelApiService {

    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun downloadHotel(): Response<HotelDto>

    @GET("8b532701-709e-4194-a41c-1a903af00195")
    suspend fun downloadRooms(): Response<RoomsListDto>

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun downloadBookingInfo(): Response<BookingInfoDto>
}
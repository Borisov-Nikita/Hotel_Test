package nik.borisov.hoteltest.data.sources.remote.model

import com.google.gson.annotations.SerializedName

data class RoomsListDto(

    @SerializedName("rooms")
    val rooms: List<RoomDto>,
)

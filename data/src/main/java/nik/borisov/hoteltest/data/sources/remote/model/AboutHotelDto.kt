package nik.borisov.hoteltest.data.sources.remote.model

import com.google.gson.annotations.SerializedName

data class AboutHotelDto(

    @SerializedName("description")
    val description: String,
    @SerializedName("peculiarities")
    val peculiarities: List<String>
)
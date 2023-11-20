package nik.borisov.hoteltest.features.rooms.domain.entities

data class Room(

    val id: Long,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>
)

package nik.borisov.hoteltest.features.hotel.domain.entities

data class Hotel(
    val id: Long,
    val name: String,
    val address: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
    val imageUrls: List<String>,
    val descriptions: String,
    val peculiarities: List<String>
)

package nik.borisov.hoteltest.features.hotel.presentation.models

import nik.borisov.hoteltest.core.presentation.extensions.preparePriceForUi
import nik.borisov.hoteltest.features.hotel.domain.entities.Hotel

data class HotelUiModel(
    val name: String,
    val address: String,
    val minimalPrice: String,
    val priceForIt: String,
    val rating: String,
    val imageUrls: List<String>,
    val descriptions: String,
    val peculiarities: List<String>
)

fun Hotel.toUiModel() = HotelUiModel(
    name = this.name,
    address = this.address,
    minimalPrice = this.minimalPrice.preparePriceForUi(),
    priceForIt = this.priceForIt.lowercase(),
    rating = "${this.rating} ${this.ratingName}",
    imageUrls = this.imageUrls,
    descriptions = this.descriptions,
    peculiarities = this.peculiarities
)

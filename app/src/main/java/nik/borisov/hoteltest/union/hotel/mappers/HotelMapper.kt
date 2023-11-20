package nik.borisov.hoteltest.union.hotel.mappers

import nik.borisov.hoteltest.data.sources.remote.model.HotelDto
import nik.borisov.hoteltest.features.hotel.domain.entities.Hotel
import javax.inject.Inject

class HotelMapper @Inject constructor() {

    fun mapDtoToEntity(dto: HotelDto): Hotel {
        return Hotel(
            id = dto.id,
            name = dto.name,
            address = dto.address,
            minimalPrice = dto.price,
            priceForIt = dto.priceForIt,
            rating = dto.rating,
            ratingName = dto.ratingName,
            imageUrls = dto.imageUrls,
            descriptions = dto.aboutTheHotel.description,
            peculiarities = dto.aboutTheHotel.peculiarities
        )
    }
}
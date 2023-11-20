package nik.borisov.hoteltest.union.booking.mappers

import nik.borisov.hoteltest.data.sources.remote.model.BookingInfoDto
import nik.borisov.hoteltest.data.sources.remote.request.OrderDataInfo
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo
import nik.borisov.hoteltest.features.booking.domain.entities.OrderInfo
import javax.inject.Inject

class BookingMapper @Inject constructor() {

    fun mapBookingInfoDtoToEntity(dto: BookingInfoDto): BookingInfo {
        return BookingInfo(
            id = dto.id,
            hotelName = dto.hotelName,
            hotelAdDress = dto.hotelAddress,
            rating = dto.rating,
            ratingName = dto.ratingName,
            departure = dto.departure,
            arrivalCountry = dto.arrivalCountry,
            tourDateStart = dto.tourDateStart,
            tourDateStop = dto.tourDateStop,
            numberOfNights = dto.numberOfNights,
            room = dto.room,
            nutrition = dto.nutrition,
            tourPrice = dto.tourPrice,
            fuelCharge = dto.fuelCharge,
            serviceCharge = dto.serviceCharge
        )
    }

    fun mapOrderInfoEntityToData(entity: OrderInfo): OrderDataInfo {
        return OrderDataInfo(
            bookingId = entity.bookingId
        )
    }
}
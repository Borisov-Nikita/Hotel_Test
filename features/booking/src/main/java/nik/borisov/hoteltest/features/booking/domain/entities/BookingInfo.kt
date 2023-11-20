package nik.borisov.hoteltest.features.booking.domain.entities

data class BookingInfo(

    val id: Long,
    val hotelName: String,
    val hotelAdDress: String,
    val rating: Int,
    val ratingName: String,
    val departure: String,
    val arrivalCountry: String,
    val tourDateStart: String,
    val tourDateStop: String,
    val numberOfNights: Int,
    val room: String,
    val nutrition: String,
    val tourPrice: Int,
    val fuelCharge: Int,
    val serviceCharge: Int
)
package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tour

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo

data class TourDelegateAdapterItem(

    val id: Long,
    val hotelName: String,
    val departure: String,
    val arrivalCountry: String,
    val tourDates: String,
    val numberOfNights: Int,
    val room: String,
    val nutrition: String
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = hotelName
}

fun BookingInfo.toTourDelegateAdapterItem() = TourDelegateAdapterItem(
    id = this.id,
    hotelName = this.hotelName,
    departure = this.departure,
    arrivalCountry = this.arrivalCountry,
    tourDates = "${this.tourDateStart} – ${this.tourDateStop}",
    numberOfNights = this.numberOfNights,
    room = this.room,
    nutrition = this.nutrition
)
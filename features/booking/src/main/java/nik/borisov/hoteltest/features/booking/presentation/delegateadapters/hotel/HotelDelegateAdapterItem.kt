package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.hotel

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo

data class HotelDelegateAdapterItem(

    val id: Long,
    val hotelName: String,
    val hotelAddress: String,
    val rating: String,
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = hotelName

}

fun BookingInfo.toHotelDelegateAdapterItem() = HotelDelegateAdapterItem(
    id = this.id,
    hotelName = this.hotelName,
    hotelAddress = this.hotelAdDress,
    rating = "${this.rating} ${this.ratingName}"
)
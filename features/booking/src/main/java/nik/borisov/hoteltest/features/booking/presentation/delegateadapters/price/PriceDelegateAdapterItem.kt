package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.price

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.core.presentation.extensions.preparePriceForUi
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo

data class PriceDelegateAdapterItem(

    val id: Long,
    val tourPrice: String,
    val fuelCharge: String,
    val serviceCharge: String,
    val totalPrice: String
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = totalPrice
}

fun BookingInfo.toPriceDelegateAdapterItem() = PriceDelegateAdapterItem(
    id = this.id,
    tourPrice = this.tourPrice.preparePriceForUi(),
    fuelCharge = this.fuelCharge.preparePriceForUi(),
    serviceCharge = this.serviceCharge.preparePriceForUi(),
    totalPrice = (this.tourPrice + this.fuelCharge + this.serviceCharge).preparePriceForUi()
)
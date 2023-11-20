package nik.borisov.hoteltest.features.rooms.presentation.delegateadapter

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.core.presentation.extensions.preparePriceForUi
import nik.borisov.hoteltest.features.rooms.domain.entities.Room

data class RoomDelegateAdapterItem(

    val id: Long,
    val name: String,
    val price: String,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>
) : DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = name
}

fun Room.toDelegateAdapterItem() = RoomDelegateAdapterItem(
    id = this.id,
    name = this.name,
    price = this.price.preparePriceForUi(),
    pricePer = this.pricePer.lowercase(),
    peculiarities = this.peculiarities,
    imageUrls = this.imageUrls
)
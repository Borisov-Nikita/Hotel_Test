package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.add

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem

data class AddTouristDelegateAdapterItem(

    val id: Long = 1
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = id
}
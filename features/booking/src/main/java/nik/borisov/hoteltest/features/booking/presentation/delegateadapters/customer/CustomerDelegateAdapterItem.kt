package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.customer

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem

data class CustomerDelegateAdapterItem(

    val id: Long = 1,
    val phone: String = "",
    val email: String = "",
    val validateNeeded: Boolean = false
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = CustomerContent(phone, email, validateNeeded)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is CustomerContent) {
            if (phone != other.phone) {
                return ChangePayload.PhoneChanged(other.phone)
            }
            if (email != other.email) {
                return ChangePayload.EmailChanged(other.email)
            }
            if (validateNeeded != other.validateNeeded) {
                return ChangePayload.ValidateNeededChanged(other.validateNeeded)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    data class CustomerContent(
        val phone: String,
        val email: String,
        val validateNeeded: Boolean
    )

    sealed class ChangePayload : DelegateAdapterItem.Payloadable {

        data class PhoneChanged(val phone: String) : ChangePayload()

        data class EmailChanged(val email: String) : ChangePayload()

        data class ValidateNeededChanged(val isNeeded: Boolean) : ChangePayload()
    }
}
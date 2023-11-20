package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tourist

import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem

data class TouristDelegateAdapterItem(

    val id: Long,
    val title: String,
    val firstName: String = "",
    val secondName: String = "",
    val dateOfBirth: String = "",
    val nationality: String = "",
    val passportNumber: String = "",
    val passportValidityPeriod: String = "",
    val validateNeeded: Boolean = false
) : DelegateAdapterItem {

    override fun id(): Any = id

    override fun content(): Any = TouristContent(
        firstName,
        secondName,
        dateOfBirth,
        nationality,
        passportNumber,
        passportValidityPeriod,
        validateNeeded
    )

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is TouristContent) {
            if (firstName != other.firstName) {
                return ChangePayload.FirstNameChanged(other.firstName)
            }
            if (secondName != other.secondName) {
                return ChangePayload.SecondNameChanged(other.secondName)
            }
            if (dateOfBirth != other.dateOfBirth) {
                return ChangePayload.DateOfBirthChanged(other.dateOfBirth)
            }
            if (nationality != other.nationality) {
                return ChangePayload.NationalityChanged(other.nationality)
            }
            if (passportNumber != other.passportNumber) {
                return ChangePayload.PassportNumberChanged(other.passportNumber)
            }
            if (passportValidityPeriod != other.passportValidityPeriod) {
                return ChangePayload.PassportValidityPeriodChanged(other.passportValidityPeriod)
            }
            if (validateNeeded != other.validateNeeded) {
                return ChangePayload.ValidateNeededChanged(other.validateNeeded)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    data class TouristContent(
        val firstName: String,
        val secondName: String,
        val dateOfBirth: String,
        val nationality: String,
        val passportNumber: String,
        val passportValidityPeriod: String,
        val validateNeeded: Boolean
    )

    sealed class ChangePayload : DelegateAdapterItem.Payloadable {

        data class FirstNameChanged(val firstName: String) : ChangePayload()

        data class SecondNameChanged(val secondName: String) : ChangePayload()

        data class DateOfBirthChanged(val dateOfBirth: String) : ChangePayload()

        data class NationalityChanged(val nationality: String) : ChangePayload()

        data class PassportNumberChanged(val passportNumber: String) : ChangePayload()

        data class PassportValidityPeriodChanged(val passportValidityPeriod: String) :
            ChangePayload()

        data class ValidateNeededChanged(val isNeeded: Boolean) : ChangePayload()
    }
}
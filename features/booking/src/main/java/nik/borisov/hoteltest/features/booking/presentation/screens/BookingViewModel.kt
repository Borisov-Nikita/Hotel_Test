package nik.borisov.hoteltest.features.booking.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.core.common.isEmail
import nik.borisov.hoteltest.core.common.isPhoneNumber
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.domain.BookingUseCase
import nik.borisov.hoteltest.features.booking.domain.entities.BookingInfo
import nik.borisov.hoteltest.features.booking.domain.entities.OrderInfo
import nik.borisov.hoteltest.features.booking.presentation.BookingRouter
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.add.AddTouristDelegateAdapterItem
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.customer.CustomerDelegateAdapterItem
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.hotel.toHotelDelegateAdapterItem
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.price.toPriceDelegateAdapterItem
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tour.toTourDelegateAdapterItem
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tourist.TouristDelegateAdapterItem
import nik.borisov.hoteltest.features.booking.presentation.screens.BookingFragment.BookingScreenArgs

class BookingViewModel @AssistedInject constructor(
    @Assisted private val bookingScreenArgs: BookingScreenArgs,
    private val bookingUseCase: BookingUseCase,
    private val bookingRouter: BookingRouter
) : ViewModel() {

    private val bookingInfoFlow = bookingUseCase.getBookingInfo(bookingScreenArgs.roomId)

    private val verifiableContentFlow = MutableStateFlow(VerifiableContent())

    val screenState =
        bookingInfoFlow.combine(verifiableContentFlow) { stateResult, verifiableContent ->
            when (stateResult) {
                is StateResult.Pending -> {
                    StateResult.Pending
                }

                is StateResult.Error -> {
                    StateResult.Error(stateResult.exception)
                }

                is StateResult.Success -> {
                    StateResult.Success(prepareScreenContent(stateResult.value, verifiableContent))
                }
            }
        }.asLiveData()

    fun doOrder() {
        viewModelScope.launch {
            if (!validateCustomerInput()) delay(50)
            if (!validateCustomerInput()) return@launch
            bookingUseCase.doBookingOrder(prepareFakeOrderInfo()).collect { stateResult ->
                when (stateResult) {
                    is StateResult.Pending -> {}
                    is StateResult.Error -> {}
                    is StateResult.Success -> {
                        launchPaidScreen(stateResult.value)
                    }
                }
            }
        }
    }

    fun addTourist() {
        if (verifiableContentFlow.value.touristsInfo.size < Tourists.entries.size) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = mutableListOf<TouristDelegateAdapterItem>().apply {
                    val tourist =
                        Tourists.values()[verifiableContentFlow.value.touristsInfo.size]
                    addAll(verifiableContentFlow.value.touristsInfo)
                    add(
                        TouristDelegateAdapterItem(
                            id = tourist.position,
                            title = tourist.title
                        )
                    )
                }
            )
        }
    }

    fun validatePhone(phone: String): Boolean {
        val isValid = phone.isPhoneNumber()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                customerInfo = verifiableContentFlow.value.customerInfo.copy(
                    phone = phone,
                    validateNeeded = false
                )
            )
        }
        return isValid
    }

    fun validateEmail(email: String): Boolean {
        val isValid = email.isEmail()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                customerInfo = verifiableContentFlow.value.customerInfo.copy(
                    email = email,
                    validateNeeded = false
                )
            )
        }
        return isValid
    }

    fun validateFirstName(firstName: String, touristId: Long): Boolean {
        val isValid = firstName.isNotBlank()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                    if (touristId == it.id) {
                        it.copy(
                            firstName = firstName,
                            validateNeeded = false
                        )
                    } else {
                        it
                    }
                }
            )
        }
        return isValid
    }

    fun validateSecondName(secondName: String, touristId: Long): Boolean {
        val isValid = secondName.isNotBlank()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                    if (touristId == it.id) {
                        it.copy(
                            secondName = secondName,
                            validateNeeded = false
                        )
                    } else {
                        it
                    }
                }
            )
        }
        return isValid
    }

    fun validateDateOfBirth(dateOfBirth: String, touristId: Long): Boolean {
        val isValid = dateOfBirth.isNotBlank()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                    if (touristId == it.id) {
                        it.copy(
                            dateOfBirth = dateOfBirth,
                            validateNeeded = false
                        )
                    } else {
                        it
                    }
                }
            )
        }
        return isValid
    }

    fun validateNationality(nationality: String, touristId: Long): Boolean {
        val isValid = nationality.isNotBlank()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                    if (touristId == it.id) {
                        it.copy(
                            nationality = nationality,
                            validateNeeded = false
                        )
                    } else {
                        it
                    }
                }
            )
        }
        return isValid
    }

    fun validatePassportNumber(passportNumber: String, touristId: Long): Boolean {
        val isValid = passportNumber.isNotBlank()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                    if (touristId == it.id) {
                        it.copy(
                            passportNumber = passportNumber,
                            validateNeeded = false
                        )
                    } else {
                        it
                    }
                }
            )
        }
        return isValid
    }

    fun validatePassportValidityPeriod(passportValidityPeriod: String, touristId: Long): Boolean {
        val isValid = passportValidityPeriod.isNotBlank()
        if (isValid) {
            verifiableContentFlow.value = verifiableContentFlow.value.copy(
                touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                    if (touristId == it.id) {
                        it.copy(
                            passportValidityPeriod = passportValidityPeriod,
                            validateNeeded = false
                        )
                    } else {
                        it
                    }
                }
            )
        }
        return isValid
    }

    private fun launchPaidScreen(orderNumber: Int) {
        bookingRouter.launchPaidScreen(orderNumber)
    }

    private fun prepareScreenContent(
        bookingInfo: BookingInfo,
        verifiableContent: VerifiableContent
    ): ScreenState {
        val list = mutableListOf<DelegateAdapterItem>().apply {
            add(bookingInfo.toHotelDelegateAdapterItem())
            add(bookingInfo.toTourDelegateAdapterItem())
            add(verifiableContent.customerInfo)
            verifiableContent.touristsInfo.forEach {
                add(it)
            }
            add(AddTouristDelegateAdapterItem())
            add(bookingInfo.toPriceDelegateAdapterItem())
        }
        return ScreenState(
            contentList = list.toList(),
            totalPrice = bookingInfo.toPriceDelegateAdapterItem().totalPrice
        )
    }

    private fun prepareFakeOrderInfo() = OrderInfo(1)

    private fun validateCustomerInput(): Boolean {
        var isValid = true
        with(verifiableContentFlow) {
            if (!value.customerInfo.phone.isPhoneNumber() ||
                !value.customerInfo.email.isEmail()
            ) {
                value = value.copy(
                    customerInfo = value.customerInfo.copy(
                        validateNeeded = true
                    )
                )
                isValid = false
            }
            value.touristsInfo.forEach { tourist ->
                if (tourist.firstName.isBlank() || tourist.secondName.isBlank() ||
                    tourist.dateOfBirth.isBlank() || tourist.nationality.isBlank() ||
                    tourist.passportNumber.isBlank() || tourist.passportValidityPeriod.isBlank()
                ) {
                    value = value.copy(
                        touristsInfo = verifiableContentFlow.value.touristsInfo.map {
                            if (tourist.id == it.id) {
                                it.copy(
                                    validateNeeded = true
                                )
                            } else {
                                it
                            }
                        }
                    )
                    isValid = false
                }
            }
        }
        return isValid
    }

    @AssistedFactory
    interface Factory {

        fun create(bookingScreenArgs: BookingScreenArgs): BookingViewModel
    }

    data class VerifiableContent(
        val customerInfo: CustomerDelegateAdapterItem = CustomerDelegateAdapterItem(),
        val touristsInfo: List<TouristDelegateAdapterItem> = mutableListOf(
            TouristDelegateAdapterItem(
                id = Tourists.FIRST.position,
                title = Tourists.FIRST.title
            )
        )
    )

    data class ScreenState(

        val contentList: List<DelegateAdapterItem>,
        val totalPrice: String
    )

    enum class Tourists(val position: Long, val title: String) {
        FIRST(1, "Первый"),
        SECOND(2, "Второй"),
        THIRD(3, "Третий"),
        FOURTH(4, "Четвертый"),
        FIFTH(5, "Пятый"),
    }
}
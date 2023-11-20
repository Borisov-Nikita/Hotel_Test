package nik.borisov.hoteltest.features.hotel.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.features.hotel.domain.HotelUseCase
import nik.borisov.hoteltest.features.hotel.presentation.HotelRouter
import nik.borisov.hoteltest.features.hotel.presentation.models.toUiModel
import nik.borisov.hoteltest.features.hotel.presentation.screens.HotelFragment.HotelScreenArgs

class HotelViewModel @AssistedInject constructor(
    @Assisted private val hotelScreenArgs: HotelScreenArgs,
    hotelUseCase: HotelUseCase,
    private val hotelRouter: HotelRouter
) : ViewModel() {

    private val hotelFlow = hotelUseCase.getHotel(hotelScreenArgs.hotelId)
    val screenState = hotelFlow.map { stateResult ->
        stateResult.map { entity ->
            entity.toUiModel()
        }
    }.onEach { stateResult ->
        if (stateResult is StateResult.Success) hotelName = stateResult.value.name
    }.asLiveData()

    private var hotelName = ""
    fun launchRoomsScreen() {
        hotelRouter.launchRoomsScreen(hotelScreenArgs.hotelId, hotelName)
    }

    @AssistedFactory
    interface Factory {

        fun create(hotelScreenArgs: HotelScreenArgs): HotelViewModel
    }
}
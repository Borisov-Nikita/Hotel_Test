package nik.borisov.hoteltest.features.rooms.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map
import nik.borisov.hoteltest.features.rooms.domain.RoomsUseCase
import nik.borisov.hoteltest.features.rooms.domain.entities.RoomSearchFilters
import nik.borisov.hoteltest.features.rooms.presentation.RoomsRouter
import nik.borisov.hoteltest.features.rooms.presentation.delegateadapter.toDelegateAdapterItem
import nik.borisov.hoteltest.features.rooms.presentation.screens.RoomsFragment.RoomsScreenArgs

class RoomsViewModel @AssistedInject constructor(
    @Assisted private val roomsScreenArgs: RoomsScreenArgs,
    roomsUseCase: RoomsUseCase,
    private val roomsRouter: RoomsRouter
) : ViewModel() {

    private val roomsFlow = roomsUseCase.getRooms(prepareFakeFilter())
    val screenState = roomsFlow.map { stateResult ->
        stateResult.map { entities ->
            entities.map {
                it.toDelegateAdapterItem()
            }
        }
    }.asLiveData()

    fun launchBookingScreen(roomId: Long) {
        roomsRouter.launchBookingScreen(roomId)
    }

    private fun prepareFakeFilter() = RoomSearchFilters(roomsScreenArgs.hotelId)

    @AssistedFactory
    interface Factory {

        fun create(roomsScreenArgs: RoomsScreenArgs): RoomsViewModel
    }
}
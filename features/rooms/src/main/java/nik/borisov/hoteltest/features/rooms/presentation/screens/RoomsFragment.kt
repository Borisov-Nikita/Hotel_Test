package nik.borisov.hoteltest.features.rooms.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.core.presentation.BaseScreenArgs
import nik.borisov.hoteltest.core.presentation.delegateadapter.CompositeAdapter
import nik.borisov.hoteltest.core.presentation.extensions.args
import nik.borisov.hoteltest.core.presentation.extensions.viewBinding
import nik.borisov.hoteltest.core.presentation.viewModelCreator
import nik.borisov.hoteltest.features.rooms.R
import nik.borisov.hoteltest.features.rooms.databinding.FragmentRoomsBinding
import nik.borisov.hoteltest.features.rooms.presentation.delegateadapter.RoomsDelegateAdapter
import javax.inject.Inject

@AndroidEntryPoint
class RoomsFragment : Fragment(R.layout.fragment_rooms) {

    private val binding by viewBinding<FragmentRoomsBinding>()

    @Inject
    lateinit var viewModelFactory: RoomsViewModel.Factory
    private val viewModel by viewModelCreator { viewModelFactory.create(args()) }

    private val compositeAdapter by lazy {
        CompositeAdapter.Builder()
            .add(RoomsDelegateAdapter { roomId ->
                viewModel.launchBookingScreen(roomId)
            })
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { stateResult ->
            when (stateResult) {
                is StateResult.Pending -> {}
                is StateResult.Error -> {}
                is StateResult.Success -> {
                    compositeAdapter.submitList(stateResult.value)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.roomRecyclerView
        recyclerView.adapter = compositeAdapter
    }

    @Parcelize
    class RoomsScreenArgs(
        val hotelId: Long
    ) : BaseScreenArgs
}
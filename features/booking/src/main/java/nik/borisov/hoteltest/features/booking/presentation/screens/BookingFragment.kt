package nik.borisov.hoteltest.features.booking.presentation.screens

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
import nik.borisov.hoteltest.features.booking.R
import nik.borisov.hoteltest.features.booking.databinding.FragmentBookingBinding
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.add.AddTouristDelegateAdapter
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.customer.CustomerDelegateAdapter
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.hotel.HotelDelegateAdapter
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.price.PriceDelegateAdapter
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tour.TourDelegateAdapter
import nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tourist.TouristDelegateAdapter
import javax.inject.Inject

@AndroidEntryPoint
class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val binding by viewBinding<FragmentBookingBinding>()

    @Inject
    lateinit var viewModelFactory: BookingViewModel.Factory
    private val viewModel by viewModelCreator { viewModelFactory.create(args()) }

    private val compositeAdapter = CompositeAdapter.Builder()
        .add(HotelDelegateAdapter())
        .add(TourDelegateAdapter())
        .add(PriceDelegateAdapter())
        .add(CustomerDelegateAdapter(
            onPhoneChanged = { viewModel.validatePhone(it) },
            onEmailChanged = { viewModel.validateEmail(it) }
        ))
        .add(
            TouristDelegateAdapter(
                onFirstNameChanged = { v, i -> viewModel.validateFirstName(v, i) },
                onSecondNameChanged = { v, i -> viewModel.validateSecondName(v, i) },
                onDateOfBirthChanged = { v, i -> viewModel.validateDateOfBirth(v, i) },
                onNationalityChanged = { v, i -> viewModel.validateNationality(v, i) },
                onPassportNumberChanged = { v, i -> viewModel.validatePassportNumber(v, i) },
                onPassportValidityPeriodChanged = { v, i ->
                    viewModel.validatePassportValidityPeriod(v, i)
                },
            )
        )
        .add(AddTouristDelegateAdapter { viewModel.addTourist() })
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupRecyclerView()

        binding.confirmButton.setOnClickListener {
            viewModel.doOrder()
        }
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { stateResult ->
            when (stateResult) {
                is StateResult.Pending -> {}
                is StateResult.Error -> {}
                is StateResult.Success -> {
                    compositeAdapter.submitList(stateResult.value.contentList)

                    binding.confirmButton.text =
                        getString(R.string.to_pay_value, stateResult.value.totalPrice)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.bookingRecyclerView
        recyclerView.adapter = compositeAdapter
    }

    @Parcelize
    class BookingScreenArgs(
        val roomId: Long
    ) : BaseScreenArgs
}
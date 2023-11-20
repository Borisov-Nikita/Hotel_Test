package nik.borisov.hoteltest.features.hotel.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import nik.borisov.hoteltest.core.common.StateResult
import nik.borisov.hoteltest.core.presentation.BaseScreenArgs
import nik.borisov.hoteltest.core.presentation.databinding.ItemPeculiarityBinding
import nik.borisov.hoteltest.core.presentation.extensions.viewBinding
import nik.borisov.hoteltest.core.presentation.slider.ImageSliderAdapter
import nik.borisov.hoteltest.core.presentation.viewModelCreator
import nik.borisov.hoteltest.features.hotel.R
import nik.borisov.hoteltest.features.hotel.databinding.FragmentHotelBinding
import javax.inject.Inject

@AndroidEntryPoint
class HotelFragment : Fragment(R.layout.fragment_hotel) {

    private val binding by viewBinding<FragmentHotelBinding>()

    @Inject
    lateinit var viewModelFactory: HotelViewModel.Factory
    private val viewModel by viewModelCreator { viewModelFactory.create(HotelScreenArgs()) }

    private val imageSliderAdapter = ImageSliderAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { stateResult ->
            when (stateResult) {
                is StateResult.Pending -> {}
                is StateResult.Error -> {}
                is StateResult.Success -> {
                    val model = stateResult.value

                    with(binding) {
                        setupPhotoSlider()
                        hotelInfoLayout.ratingTextView.text = model.rating
                        hotelInfoLayout.hotelNameTextView.text = model.name
                        hotelInfoLayout.hotelAddressButton.text = model.address
                        priceTextView.text = getString(nik.borisov.hoteltest.core.presentation.R.string.price_from, model.minimalPrice)
                        pricePerTextView.text = model.priceForIt
                        model.peculiarities.forEach {
                            val peculiarityItem =
                                ItemPeculiarityBinding.inflate(layoutInflater).apply {
                                    peculiarityTextView.text = it
                                }
                            peculiaritiesLayout.addView(peculiarityItem.root)
                        }
                        descriptionTextView.text = model.descriptions
                    }

                    imageSliderAdapter.submitList(model.imageUrls)
                }
            }
        }
    }

    private fun setupPhotoSlider() {
        val viewPager = binding.photoSliderLayout.imageSliderViewPager
        val tabLayout = binding.photoSliderLayout.dotsIndicatorTabLayout

        viewPager.adapter = imageSliderAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        }.attach()
    }

    private fun setupClickListeners() {
        binding.toRoomsButton.setOnClickListener {
            viewModel.launchRoomsScreen()
        }
    }

    @Parcelize
    class HotelScreenArgs(
        val hotelId: Long = 1
    ) : BaseScreenArgs
}
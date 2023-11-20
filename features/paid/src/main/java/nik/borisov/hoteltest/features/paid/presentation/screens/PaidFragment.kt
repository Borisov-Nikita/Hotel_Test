package nik.borisov.hoteltest.features.paid.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import nik.borisov.hoteltest.core.presentation.BaseScreenArgs
import nik.borisov.hoteltest.core.presentation.extensions.args
import nik.borisov.hoteltest.core.presentation.extensions.viewBinding
import nik.borisov.paid.R
import nik.borisov.paid.databinding.FragmentPaidBinding

@AndroidEntryPoint
class PaidFragment : Fragment(R.layout.fragment_paid) {

    private val binding by viewBinding<FragmentPaidBinding>()

    private val viewModel by viewModels<PaidViewModel>()

    private val args by lazy { args<PaidScreenArgs>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.descriptionTextView.text =
            getString(R.string.description_paid_processed, args.orderNumber)
        binding.confirmButton.setOnClickListener {
            viewModel.popBack()
        }
    }

    @Parcelize
    class PaidScreenArgs(
        val orderNumber: Int
    ) : BaseScreenArgs
}
package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.customer

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.textfield.TextInputLayout
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.core.theme.R.color
import nik.borisov.hoteltest.features.booking.databinding.ItemCustomerInfoBinding

class CustomerDelegateAdapter(
    private val onPhoneChanged: (String) -> Boolean,
    private val onEmailChanged: (String) -> Boolean
) :
    DelegateAdapter<CustomerDelegateAdapterItem, CustomerDelegateAdapter.CustomerViewHolder>(
        CustomerDelegateAdapterItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return CustomerViewHolder(
            ItemCustomerInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onPhoneChanged,
            onEmailChanged
        )
    }

    override fun bindViewHolder(
        item: CustomerDelegateAdapterItem,
        viewHolder: CustomerViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class CustomerViewHolder(
        private val binding: ItemCustomerInfoBinding,
        private val onPhoneChanged: (String) -> Boolean,
        private val onEmailChanged: (String) -> Boolean
    ) : ViewHolder(binding.root) {

        fun bind(item: CustomerDelegateAdapterItem) {
            with(binding) {
                phoneEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        validatePhone()
                    } else {
                        changeEditTextBackground(phoneTextInputLayout, color.recycler_view_bg)
                    }
                }

                emailEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        validateEmail()
                    } else {
                        changeEditTextBackground(emailTextInputLayout, color.recycler_view_bg)
                    }
                }

                if (item.validateNeeded) {
                    validatePhone()
                    validateEmail()
                }
            }
        }

        private fun getPhoneString() = binding.phoneEditText.getRawText()

        private fun getEmailString() = binding.emailEditText.text?.toString() ?: ""

        private fun validatePhone() {
            if (!onPhoneChanged(getPhoneString())) {
                changeEditTextBackground(
                    binding.phoneTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun validateEmail() {
            if (!onEmailChanged(getEmailString())) {
                changeEditTextBackground(
                    binding.emailTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun changeEditTextBackground(view: TextInputLayout, @ColorRes id: Int) {
            view.boxBackgroundColor = ContextCompat.getColor(
                itemView.context,
                id
            )
        }
    }
}
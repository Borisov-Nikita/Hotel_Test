package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tourist

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.textfield.TextInputLayout
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.core.presentation.delegateadapter.getValueAnimator
import nik.borisov.hoteltest.core.theme.R.color
import nik.borisov.hoteltest.features.booking.R
import nik.borisov.hoteltest.features.booking.databinding.ItemTouristInfoBinding

class TouristDelegateAdapter(
    private val onFirstNameChanged: (value: String, id: Long) -> Boolean,
    private val onSecondNameChanged: (value: String, id: Long) -> Boolean,
    private val onDateOfBirthChanged: (value: String, id: Long) -> Boolean,
    private val onNationalityChanged: (value: String, id: Long) -> Boolean,
    private val onPassportNumberChanged: (value: String, id: Long) -> Boolean,
    private val onPassportValidityPeriodChanged: (value: String, id: Long) -> Boolean
) :
    DelegateAdapter<TouristDelegateAdapterItem, TouristDelegateAdapter.CustomerViewHolder>(
        TouristDelegateAdapterItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return CustomerViewHolder(
            ItemTouristInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onFirstNameChanged,
            onSecondNameChanged,
            onDateOfBirthChanged,
            onNationalityChanged,
            onPassportNumberChanged,
            onPassportValidityPeriodChanged
        )
    }

    override fun bindViewHolder(
        item: TouristDelegateAdapterItem,
        viewHolder: CustomerViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class CustomerViewHolder(
        private val binding: ItemTouristInfoBinding,
        private val onFirstNameChanged: (value: String, id: Long) -> Boolean,
        private val onSecondNameChanged: (value: String, id: Long) -> Boolean,
        private val onDateOfBirthChanged: (value: String, id: Long) -> Boolean,
        private val onNationalityChanged: (value: String, id: Long) -> Boolean,
        private val onPassportNumberChanged: (value: String, id: Long) -> Boolean,
        private val onPassportValidityPeriodChanged: (value: String, id: Long) -> Boolean
    ) : ViewHolder(binding.root) {

        private val expandableContainer = binding.root
        private val expandView = binding.hiddenContainer
        private val indicator = binding.indicatorImageView

        private val listItemExpandDuration: Long = (300L / 0.8f).toLong()

        private var isExpanded = true

        fun bind(item: TouristDelegateAdapterItem) {
            with(binding) {
                titleTextView.text =
                    itemView.context.getString(R.string.tourist, item.title)

                indicatorLayout.setOnClickListener {
                    expandItem()
                }

                nameEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        validateFirstName(item.id)
                    } else {
                        changeEditTextBackground(nameTextInputLayout, color.recycler_view_bg)
                    }
                }

                surnameEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        validateSecondName(item.id)
                    } else {
                        changeEditTextBackground(surnameTextInputLayout, color.recycler_view_bg)
                    }
                }

                dateOfBirthEditText.onFocusChangeListener =
                    OnFocusChangeListener { view, hasFocus ->
                        if (!hasFocus) {
                            validateDateOfBirth(item.id)
                        } else {
                            changeEditTextBackground(
                                dateOfBirthTextInputLayout,
                                color.recycler_view_bg
                            )
                        }
                    }

                nationalityEditText.onFocusChangeListener =
                    OnFocusChangeListener { view, hasFocus ->
                        if (!hasFocus) {
                            validateNationality(item.id)
                        } else {
                            changeEditTextBackground(
                                nationalityTextInputLayout,
                                color.recycler_view_bg
                            )
                        }
                    }

                passportNumberEditText.onFocusChangeListener =
                    OnFocusChangeListener { view, hasFocus ->
                        if (!hasFocus) {
                            validatePassportNumber(item.id)
                        } else {
                            changeEditTextBackground(
                                passportNumberTextInputLayout,
                                color.recycler_view_bg
                            )
                        }
                    }

                passportValidityPeriodEditText.onFocusChangeListener =
                    OnFocusChangeListener { view, hasFocus ->
                        if (!hasFocus) {
                            validatePassportValidityPeriod(item.id)
                        } else {
                            changeEditTextBackground(
                                passportValidityPeriodTextInputLayout,
                                color.recycler_view_bg
                            )
                        }
                    }

                if (item.validateNeeded) {
                    validateFirstName(item.id)
                    validateSecondName(item.id)
                    validateDateOfBirth(item.id)
                    validateNationality(item.id)
                    validatePassportNumber(item.id)
                    validatePassportValidityPeriod(item.id)
                }
            }
        }

        private fun expandItem() {
            val animator = getValueAnimator(
                isExpanded, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) { progress -> setExpandProgress(progress) }
            if (!isExpanded) animator.doOnStart { expandView.visibility = View.VISIBLE }
            else animator.doOnEnd { expandView.visibility = View.GONE }
            animator.start()
            isExpanded = !isExpanded
        }

        private fun setExpandProgress(progress: Float) {
            expandableContainer.requestLayout()
            indicator.rotation = 180 * progress
        }

        private fun getFirstNameString() = binding.nameEditText.text?.toString() ?: ""

        private fun getSecondNameString() = binding.surnameEditText.text?.toString() ?: ""

        private fun getDateOfBirthString() = binding.dateOfBirthEditText.text?.toString() ?: ""

        private fun getNationalityString() = binding.nationalityEditText.text?.toString() ?: ""

        private fun getPassportNumberString() =
            binding.passportNumberEditText.text?.toString() ?: ""

        private fun getPassportValidityPeriodString() =
            binding.passportValidityPeriodEditText.text?.toString() ?: ""

        private fun validateFirstName(itemId: Long) {
            if (!onFirstNameChanged(getFirstNameString(), itemId)) {
                changeEditTextBackground(
                    binding.nameTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun validateSecondName(itemId: Long) {
            if (!onSecondNameChanged(getSecondNameString(), itemId)) {
                changeEditTextBackground(
                    binding.surnameTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun validateDateOfBirth(itemId: Long) {
            if (!onDateOfBirthChanged(getDateOfBirthString(), itemId)) {
                changeEditTextBackground(
                    binding.dateOfBirthTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun validateNationality(itemId: Long) {
            if (!onNationalityChanged(getNationalityString(), itemId)) {
                changeEditTextBackground(
                    binding.nationalityTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun validatePassportNumber(itemId: Long) {
            if (!onPassportNumberChanged(getPassportNumberString(), itemId)) {
                changeEditTextBackground(
                    binding.passportNumberTextInputLayout,
                    color.edit_text_error_color
                )
            }
        }

        private fun validatePassportValidityPeriod(itemId: Long) {
            if (!onPassportValidityPeriodChanged(getPassportValidityPeriodString(), itemId)) {
                changeEditTextBackground(
                    binding.passportValidityPeriodTextInputLayout,
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
package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.tour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.R
import nik.borisov.hoteltest.features.booking.databinding.ItemBookingDetailsBinding

class TourDelegateAdapter :
    DelegateAdapter<TourDelegateAdapterItem, TourDelegateAdapter.TourViewHolder>(
        TourDelegateAdapterItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return TourViewHolder(
            ItemBookingDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        item: TourDelegateAdapterItem,
        viewHolder: TourViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class TourViewHolder(
        private val binding: ItemBookingDetailsBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: TourDelegateAdapterItem) {
            with(binding) {
                departureValueTextView.text = item.departure
                arrivalCountryValueTextView.text = item.arrivalCountry
                datesValueTextView.text = item.tourDates
                numberOfNightsValueTextView.text =
                    itemView.context.getString(
                        R.string.number_of_nights_value,
                        item.numberOfNights
                    )
                hotelValueTextView.text = item.hotelName
                roomValueTextView.text = item.room
                nutritionValueTextView.text = item.nutrition
            }
        }
    }
}
package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.hotel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.databinding.ItemHotelBinding

class HotelDelegateAdapter :
    DelegateAdapter<HotelDelegateAdapterItem, HotelDelegateAdapter.HotelViewHolder>(
        HotelDelegateAdapterItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return HotelViewHolder(
            ItemHotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        item: HotelDelegateAdapterItem,
        viewHolder: HotelViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class HotelViewHolder(
        private val binding: ItemHotelBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: HotelDelegateAdapterItem) {
            with(binding) {
                layoutBasic.ratingTextView.text = item.rating
                layoutBasic.hotelNameTextView.text = item.hotelName
                layoutBasic.hotelAddressButton.text = item.hotelAddress
            }
        }
    }
}
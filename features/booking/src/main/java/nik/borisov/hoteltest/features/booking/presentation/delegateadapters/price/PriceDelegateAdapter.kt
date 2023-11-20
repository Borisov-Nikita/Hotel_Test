package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.price

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.databinding.ItemPriceBinding

class PriceDelegateAdapter :
    DelegateAdapter<PriceDelegateAdapterItem, PriceDelegateAdapter.PriceViewHolder>(
        PriceDelegateAdapterItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return PriceViewHolder(
            ItemPriceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        item: PriceDelegateAdapterItem,
        viewHolder: PriceViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class PriceViewHolder(
        private val binding: ItemPriceBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: PriceDelegateAdapterItem) {
            with(binding) {
                tourValueTextView.text = item.tourPrice
                fuelSurchargeValueTextView.text = item.fuelCharge
                serviceFeeValueTextView.text = item.serviceCharge
                toPayValueTextView.text = item.totalPrice
            }
        }
    }
}
package nik.borisov.hoteltest.features.booking.presentation.delegateadapters.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.features.booking.databinding.ItemAddTouristBinding

class AddTouristDelegateAdapter(
    private val onAddButtonChecked: () -> Unit
) :
    DelegateAdapter<AddTouristDelegateAdapterItem, AddTouristDelegateAdapter.HotelViewHolder>(
        AddTouristDelegateAdapterItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return HotelViewHolder(
            ItemAddTouristBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onAddButtonChecked
        )
    }

    override fun bindViewHolder(
        item: AddTouristDelegateAdapterItem,
        viewHolder: HotelViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class HotelViewHolder(
        private val binding: ItemAddTouristBinding,
        private val onAddButtonChecked: () -> Unit
    ) : ViewHolder(binding.root) {

        fun bind(item: AddTouristDelegateAdapterItem) {
            binding.addButtonLayout.setOnClickListener {
                onAddButtonChecked()
            }
        }
    }
}
package nik.borisov.hoteltest.features.rooms.presentation.delegateadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.tabs.TabLayoutMediator
import nik.borisov.hoteltest.core.presentation.databinding.ItemPeculiarityBinding
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapter
import nik.borisov.hoteltest.core.presentation.delegateadapter.DelegateAdapterItem
import nik.borisov.hoteltest.core.presentation.slider.ImageSliderAdapter
import nik.borisov.hoteltest.features.rooms.databinding.ItemRoomDescriptionBinding

class RoomsDelegateAdapter(
    private val onChooseRoomClicked: (Long) -> Unit
) : DelegateAdapter<RoomDelegateAdapterItem, RoomsDelegateAdapter.RoomsViewHolder>(
    RoomDelegateAdapterItem::class.java
) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return RoomsViewHolder(
            ItemRoomDescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onChooseRoomClicked
        )
    }

    override fun bindViewHolder(
        item: RoomDelegateAdapterItem,
        viewHolder: RoomsViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(item)
    }

    class RoomsViewHolder(
        private val binding: ItemRoomDescriptionBinding,
        private val onButtonClicked: (Long) -> Unit
    ) : ViewHolder(binding.root) {

        private val imageSliderAdapter = ImageSliderAdapter()

        fun bind(item: RoomDelegateAdapterItem) {
            setupPhotoSlider()
            imageSliderAdapter.submitList(item.imageUrls)
            with(binding) {
                titleTextView.text = item.name
                item.peculiarities.forEach {
                    val peculiarityItem =
                        ItemPeculiarityBinding.inflate(LayoutInflater.from(itemView.context))
                            .apply {
                                peculiarityTextView.text = it
                            }
                    peculiaritiesLayout.addView(peculiarityItem.root)
                }
                priceTextView.text = itemView.context.getString(
                    nik.borisov.hoteltest.core.presentation.R.string.price,
                    item.price
                )
                pricePerTextView.text = item.pricePer
                chooseRoomButton.setOnClickListener {
                    onButtonClicked(item.id)
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
    }
}
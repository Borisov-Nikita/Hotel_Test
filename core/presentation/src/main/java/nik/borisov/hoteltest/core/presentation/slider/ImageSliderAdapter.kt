package nik.borisov.hoteltest.core.presentation.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nik.borisov.hoteltest.core.presentation.R
import nik.borisov.hoteltest.core.presentation.databinding.ItemPhotoSliderBinding

class ImageSliderAdapter : ListAdapter<String, ImageSliderViewHolder>(ImageDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        return ImageSliderViewHolder(
            ItemPhotoSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageUrl = currentList[position]
        holder.bind(imageUrl)
    }

}

class ImageSliderViewHolder(
    private val binding: ItemPhotoSliderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.image_holder)
            .into(binding.photoImageView)
    }
}

class ImageDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}
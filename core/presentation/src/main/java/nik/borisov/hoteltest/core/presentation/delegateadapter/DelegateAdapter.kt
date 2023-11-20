package nik.borisov.hoteltest.core.presentation.delegateadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DelegateAdapter<I, in VH : RecyclerView.ViewHolder>(val modelClass: Class<out I>) {

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun bindViewHolder(
        item: I,
        viewHolder: VH,
        payloads: List<DelegateAdapterItem.Payloadable>
    )

    open fun onViewRecycled(viewHolder: VH) = Unit

    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit

    open fun onViewAttachedToWindow(viewHolder: VH) = Unit
}
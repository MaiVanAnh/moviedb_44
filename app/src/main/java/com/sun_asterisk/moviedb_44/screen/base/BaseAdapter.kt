package com.sun_asterisk.moviedb_44.screen.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var data = mutableListOf<T>()

    fun replaceItems(item: List<T>) {
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        data.add(item)
        notifyItemInserted(data.indexOf(item))
    }

    fun addItems(items: List<T>) {
        val originSize = data.size
        data.addAll(items)
        notifyItemRangeInserted(originSize, items.size)
    }

    fun removeItem(item: T) {
        if (item in data) {
            val positionItem = data.indexOf(item)
            data.removeAt(positionItem)
            notifyItemRemoved(positionItem)
        }
    }

    fun clearList() {
        data.clear()
        notifyDataSetChanged()
    }

    protected abstract fun layout(row: Int): Int

    protected abstract fun viewHolder(binding: ViewDataBinding): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BaseViewHolder<T> {
        val binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false) as ViewDataBinding
        return viewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = layout(position)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindData(data[position])
    }
}

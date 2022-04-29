package com.htf.myweatherlibrary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.htf.myweatherlibrary.R
import com.htf.myweatherlibrary.databinding.RowWeatherBinding
import com.htf.myweatherlibrary.models.DailyItem


class DailyItemAdapter(
    var itemList: ArrayList<DailyItem>,

) : RecyclerView.Adapter<DailyItemAdapter.RowViewHolder>() {

    inner class RowViewHolder(private var binding: RowWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItem: DailyItem) {
            binding.dailyItem=dataItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RowViewHolder {
        val binding: RowWeatherBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_weather,
            parent,
            false
        )
        return RowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setNewList(item: ArrayList<DailyItem>) {
        val oldList = itemList
        this.itemList = item
        val diffUtil = DiffUtil.calculateDiff(ItemDiffUtil(oldList, this.itemList))
        diffUtil.dispatchUpdatesTo(this)
    }

    fun addItem(item: DailyItem) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }


}

class ItemDiffUtil(
    private val oldList: List<DailyItem>,
    private val newList: List<DailyItem>

) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }



}
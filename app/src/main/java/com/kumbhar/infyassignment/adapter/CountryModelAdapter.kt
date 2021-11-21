package com.kumbhar.infyassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumbhar.infyassignment.databinding.RowItemBinding
import com.kumbhar.infyassignment.model.DataRows

class CountryModelAdapter(private val dataList: List<DataRows>) :
    RecyclerView.Adapter<CountryModelAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = RowItemBinding.inflate(inflater, parent, false)
        return DataViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    class DataViewHolder(private val itemBinding: RowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DataRows) {
            itemBinding.listDataItem = item
        }
    }
}
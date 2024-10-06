package com.fhanafi.winepedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fhanafi.winepedia.databinding.ItemRowWineBinding

class ListWineAdapter(private val listWine: ArrayList<Wine>) : RecyclerView.Adapter<ListWineAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // Inflate the layout using View Binding
        val binding = ItemRowWineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listWine[position]
        holder.bind(name, description, photo)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listWine[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listWine.size

    inner class ListViewHolder(private val binding: ItemRowWineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, description: String, photo: Int) {
            binding.imgItemPhoto.setImageResource(photo)
            binding.tvItemName.text = name
            binding.tvItemDescription.text = description
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Wine)
    }
}
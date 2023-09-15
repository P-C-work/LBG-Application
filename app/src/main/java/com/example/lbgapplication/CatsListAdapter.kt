package com.example.lbgapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lbgapplication.databinding.AdapterCatsListBinding
import com.example.lbgapplication.ui.CatsDataModel
import com.squareup.picasso.Picasso

class CatsListAdapter(val context: Context, private val catsCallBack: CatDetailCallBack) :
    ListAdapter<CatsDataModel, CatsListAdapter.ItemViewHolder>(CatsDiffCallBack()) {
    class CatsDiffCallBack : DiffUtil.ItemCallback<CatsDataModel>() {
        override fun areItemsTheSame(
            oldItem: CatsDataModel,
            newItem: CatsDataModel
        ): Boolean {
            return (oldItem.id == newItem.id)

        }

        override fun areContentsTheSame(
            oldItem: CatsDataModel,
            newItem: CatsDataModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AdapterCatsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }


    inner class ItemViewHolder(val binding: AdapterCatsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CatsDataModel) {
            Picasso.get()
                .load(item.url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .into(binding.ivCats);


        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            catsCallBack.callForCatDetails(currentList[position])
        }

    }

    interface CatDetailCallBack {
        fun callForCatDetails(cat: CatsDataModel)
    }
}
package com.m7mdabaza.efinancetask.ui.screens.transactionsHistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.efinancetask.databinding.ItemTransactionHistoryBinding
import com.m7mdabaza.efinancetask.utils.timestampToReadable

import javax.inject.Inject

class TransactionsHistoryAdapter @Inject constructor() :
    ListAdapter<TransactionsHistory, TransactionsHistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTransactionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.bind(getItem(position), position)
        } catch (e: Exception) {
            //holder.bind("Android", position)
        }
    }

    inner class ViewHolder(val binding: ItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: TransactionsHistory, position: Int) {

            binding.tvAmount.text = "Amount : ${data.amount} L.E"
            binding.tvTime.text = "Time : ${data.timestamp.timestampToReadable()}"

            binding.ivDelete.setOnClickListener {
                onDeleteClickListener?.let { it1 -> it1(data) }
            }

            binding.executePendingBindings()
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<TransactionsHistory>() {
        override fun areItemsTheSame(
            oldItem: TransactionsHistory, newItem: TransactionsHistory
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: TransactionsHistory, newItem: TransactionsHistory
        ): Boolean = newItem == oldItem
    }

    private var onDeleteClickListener: ((TransactionsHistory) -> Unit)? = null

    fun setDeleteClickListener(listener: (TransactionsHistory) -> Unit) {
        onDeleteClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(favourite: TransactionsHistory) {
        val list = ArrayList<TransactionsHistory>(this.currentList)
        list.remove(favourite)
        submitList(list)
        notifyDataSetChanged()
    }
}
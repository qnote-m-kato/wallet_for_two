package com.example.walletfortwo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.databinding.LayoutDialogListItemBinding
import com.example.walletfortwo.model.ExpenditureItem

class ListDialogAdapter(
    private val list: List<ExpenditureItem>,
    private val listener: OnSelectItemListener,
    private val lifecycleOwner: LifecycleOwner,
): RecyclerView.Adapter<ListDialogAdapter.ViewHolder>() {
    interface OnSelectItemListener {
        fun onSelect(item: ExpenditureItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutDialogListItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            icExpenditure.setImageResource(list[position].resource)
            textExpenditure.text = list[position].name

            container.setOnClickListener {
                listener.onSelect(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: LayoutDialogListItemBinding) : RecyclerView.ViewHolder(binding.root)

}
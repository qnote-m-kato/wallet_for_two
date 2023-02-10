package com.example.walletfortwo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.databinding.LayoutDialogGridItemBinding
import com.example.walletfortwo.model.User

class GridDialogAdapter(
    private val list: List<Int>,
    private val id: Int,
    private val usedColor: Int,
    private val context: Context,
    private val listener: OnSelectItemListener,
    private val lifecycleOwner: LifecycleOwner,
): RecyclerView.Adapter<GridDialogAdapter.ViewHolder>() {
    interface OnSelectItemListener {
        fun onSelect(id: Int, item: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutDialogGridItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            icCircle.setColorFilter(ContextCompat.getColor(context, list[position]))

            if (usedColor == list[position]) {
                icOutlineCircle.visibility = View.VISIBLE
                container.isEnabled = false
            }

            container.setOnClickListener {
                listener.onSelect(id, list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: LayoutDialogGridItemBinding) : RecyclerView.ViewHolder(binding.root)

}
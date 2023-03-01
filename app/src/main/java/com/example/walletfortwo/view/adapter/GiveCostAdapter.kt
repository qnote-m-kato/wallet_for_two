package com.example.walletfortwo.view.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.LayoutListItemCostBinding
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.repository.UserRepository

class GiveCostAdapter(
    private var list: List<GiveCost>,
    private val context: Context,
    private val listener: OnSelectItemListener,
    private val resources: Resources,
    private val lifecycleOwner: LifecycleOwner,
): ListAdapter<GiveCost, GiveCostAdapter.ViewHolder>(DiffCallback<GiveCost>(false))  {

    interface OnSelectItemListener {
        fun onSelect(item: GiveCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutListItemCostBinding.inflate(LayoutInflater.from(parent.context))
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textYear.text = list[position].date.substring(0, 4)
            textMd.text = list[position].date.substring(5)
            icArrow.visibility = View.VISIBLE
            icUserB.visibility = View.VISIBLE

            icUserA.setColorFilter(ContextCompat.getColor(context, UserRepository.getUserResource(list[position].fromUserId)))
            icUserB.setColorFilter(ContextCompat.getColor(context, UserRepository.getUserResource(list[position].toUserId)))
            icExpenditure.setImageResource(list[position].expenditureItemResource)
            textMoney.text = resources.getString(R.string.money_format).format(list[position].cost)

            if (list[position].remarks != "") {
                textRemarks.text = list[position].remarks
                icDown.setColorFilter(ContextCompat.getColor(context, R.color.purple_200))
                icDown.isEnabled = true
            } else {
                icDown.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                icDown.isEnabled = false
            }

            icDown.setOnClickListener {
                icDown.visibility = View.GONE
                icUp.visibility = View.VISIBLE
                containerRemarks.visibility = View.VISIBLE
            }

            icUp.setOnClickListener {
                icDown.visibility = View.VISIBLE
                icUp.visibility = View.GONE
                containerRemarks.visibility = View.GONE
            }

            container.setOnLongClickListener {
                listener.onSelect(list[position])
                return@setOnLongClickListener true
            }
        }
    }

    class ViewHolder(val binding: LayoutListItemCostBinding) : RecyclerView.ViewHolder(binding.root)
}

package com.example.walletfortwo.view.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.LayoutListItemCostBinding
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost

class UserDetailAdapter<Item : Any> (
    private val list: List<Item>,
    private val isLifeCost: Boolean,
    private val context: Context,
    private val resources: Resources,
    private val lifecycleOwner: LifecycleOwner,
): ListAdapter<Item, UserDetailAdapter.ViewHolder>(DiffCallback<Item>(isLifeCost)) {

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

            icUserA.visibility = View.GONE
            icArrow.visibility = View.GONE
            icUserB.visibility = View.GONE

            textYear.text = getYear(list[position])
            textMd.text = getMD(list[position])

            icExpenditure.setImageResource(getExpenditure(list[position]))
            textMoney.text = resources.getString(R.string.money_format).format(getCost(list[position]))

            val remarks = getRemarks(list[position])
            if (remarks != "") {
                textRemarks.text = remarks
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

        }
    }

    private fun getYear(item: Item): String {
        return if (isLifeCost) {
            (item as LifeCost).date.substring(0, 4)
        } else {
            (item as GiveCost).date.substring(0, 4)
        }
    }

    private fun getMD(item: Item): String {
        return if (isLifeCost) {
            (item as LifeCost).date.substring(5)
        } else {
            (item as GiveCost).date.substring(5)
        }
    }

    private fun getExpenditure(item: Item): Int {
        return if (isLifeCost) {
            (item as LifeCost).expenditureItemResource
        } else {
            (item as GiveCost).expenditureItemResource
        }
    }

    private fun getCost(item: Item): Int {
        return if (isLifeCost) {
            (item as LifeCost).cost
        } else {
            (item as GiveCost).cost
        }
    }

    private fun  getRemarks(item: Item): String {
        val remarks: String? = if (isLifeCost) {
            (item as LifeCost).remarks
        } else {
            (item as GiveCost).remarks
        }

        return remarks ?: ""
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: LayoutListItemCostBinding) : RecyclerView.ViewHolder(binding.root)

}
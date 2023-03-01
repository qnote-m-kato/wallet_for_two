package com.example.walletfortwo.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost

class DiffCallback<Item : Any>(private val isLifeCost: Boolean) : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return if (isLifeCost) {
            (oldItem as LifeCost).id == (newItem as LifeCost).id
        } else{
            (oldItem as GiveCost).id == (newItem as GiveCost).id
        }
    }
}
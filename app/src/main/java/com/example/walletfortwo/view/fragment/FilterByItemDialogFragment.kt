package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.walletfortwo.databinding.LayoutDialogFilterByItemBinding

class FilterByItemDialogFragment : DialogFragment() {
    interface OnReflectListener {
        fun onReflect(checkList: List<Boolean>)
    }
    private var listener: OnReflectListener? = null

    companion object {
        fun newInstance(checkList: List<Boolean>): FilterByItemDialogFragment {
            val args = Bundle()
            args.putBooleanArray("list", checkList.toBooleanArray())
            val fragment = FilterByItemDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutDialogFilterByItemBinding.inflate(inflater, container, false).apply {
            val current = arguments?.getBooleanArray("list")?.toList()
            val itemList = mutableListOf(rent, food, expense, electric, gas, water, wifi, other)
            if (current != null) {
                itemList.forEachIndexed { index, item ->
                    item.isChecked = current[index]
                }
            }

            allSelect.setOnClickListener {
                itemList.forEach {
                    it.isChecked = true
                }
            }

            allLift.setOnClickListener {
                itemList.forEach {
                    it.isChecked = false
                }
            }

            buttonReflect.setOnClickListener {
                val list = mutableListOf<Boolean>()
                itemList.forEachIndexed { index, item ->
                    list.add(index, item.isChecked)
                }
                listener?.onReflect(list)
                dismiss()
            }
        }.root
    }

    fun setListener(listener: OnReflectListener) {
        this.listener = listener
    }

}
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
            if (current != null) {
                rent.isChecked = current[0]
                food.isChecked = current[1]
                expense.isChecked = current[2]
                electric.isChecked = current[3]
                gas.isChecked = current[4]
                water.isChecked = current[5]
                wifi.isChecked = current[6]
                other.isChecked = current[7]
            }

            allSelect.setOnClickListener {
                setChecked(this, true)
            }

            allLift.setOnClickListener {
                setChecked(this, false)
            }

            buttonReflect.setOnClickListener {
                val list = mutableListOf<Boolean>()
                list.add(0, rent.isChecked)
                list.add(1, food.isChecked)
                list.add(2, expense.isChecked)
                list.add(3, electric.isChecked)
                list.add(4, gas.isChecked)
                list.add(5, water.isChecked)
                list.add(6, wifi.isChecked)
                list.add(7, other.isChecked)
                listener?.onReflect(list)
                dismiss()
            }
        }.root
    }

    private fun setChecked(binding: LayoutDialogFilterByItemBinding, isChecked: Boolean) {
        binding.apply {
            rent.isChecked = isChecked
            food.isChecked = isChecked
            expense.isChecked = isChecked
            electric.isChecked = isChecked
            gas.isChecked = isChecked
            water.isChecked = isChecked
            wifi.isChecked = isChecked
            other.isChecked = isChecked
        }
    }

    fun setListener(listener: OnReflectListener) {
        this.listener = listener
    }

}
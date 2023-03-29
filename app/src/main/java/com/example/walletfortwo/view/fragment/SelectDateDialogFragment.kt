package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.walletfortwo.databinding.LayoutDialogSelectDateBinding
import java.time.LocalDate

class SelectDateDialogFragment : DialogFragment() {

    interface OnSelectItemListener {
        fun onSelectDate(year: Int, month: Int)
    }

    private var listener: OnSelectItemListener? = null
    private var nowYear = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutDialogSelectDateBinding.inflate(inflater, container, false).apply {
            nowYear = LocalDate.now().year
            dateYear.text = nowYear.toString()

            buttonNext.setOnClickListener {
                nowYear += 1
                dateYear.text = nowYear.toString()
            }

            buttonBack.setOnClickListener {
                nowYear -= 1
                dateYear.text = nowYear.toString()
            }

            val buttonList = mutableListOf(all, january, february, march, april, may, june, july, august, september, october, november, december)
            buttonList.forEachIndexed { index, month ->
                month.setOnClickListener {
                    listener?.onSelectDate(nowYear, index)
                    dismiss()
                }
            }
        }.root
    }

    fun setListener(listener: OnSelectItemListener) {
        this.listener = listener
    }
}
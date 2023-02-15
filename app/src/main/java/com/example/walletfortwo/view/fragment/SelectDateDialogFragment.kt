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

            january.setOnClickListener {
                selectMonth(1)
            }

            february.setOnClickListener {
                selectMonth(2)
            }

            march.setOnClickListener {
                selectMonth(3)
            }

            april.setOnClickListener {
                selectMonth(4)
            }

            may.setOnClickListener {
                selectMonth(5)
            }

            june.setOnClickListener {
                selectMonth(6)
            }

            july.setOnClickListener {
                selectMonth(7)
            }

            august.setOnClickListener {
                selectMonth(8)
            }

            september.setOnClickListener {
                selectMonth(9)
            }

            october.setOnClickListener {
                selectMonth(10)
            }

            november.setOnClickListener {
                selectMonth(11)
            }

            december.setOnClickListener {
                selectMonth(12)
            }

            all.setOnClickListener {
                selectMonth(0)
            }
        }.root
    }

    private fun selectMonth(month: Int) {
        listener?.onSelectDate(nowYear, month)
        dismiss()
    }

    fun setListener(listener: OnSelectItemListener) {
        this.listener = listener
    }
}
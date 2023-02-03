package com.example.walletfortwo.view.fragment

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentLifeCostAddBinding
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.viewModel.AddFragmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

//生活費の追加画面
class LifeCostAddFragment : Fragment() {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[AddFragmentViewModel::class.java]
        }
    }
    private lateinit var binding: FragmentLifeCostAddBinding
    companion object {
        fun newInstance(): LifeCostAddFragment {
            val args = Bundle()
            val fragment = LifeCostAddFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnAddListener {
        fun onAdd(lifeCost: LifeCost)
    }

    private var listener: OnAddListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLifeCostAddBinding.inflate(inflater, container, false).apply {
            containerUser.icUser.setColorFilter(R.color.purple_200)

            containerDate.textDate.setOnClickListener {
                showDatePicker()
            }

            containerDate.buttonToday.setOnClickListener {
                val date = LocalDate.now()
                val format = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                val toDay = date.format(format)

                containerDate.textDate.text = toDay
                viewModel?.dateValidation?.postValue(true)
            }

            containerUser.buttonUser.setOnClickListener {
                val users = viewModel?.getUserNames()!!
                showSelectUserDialog(users)
            }

            containerMoney.editTextMoney.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (containerMoney.editTextMoney.text.isNotEmpty()) {
                        viewModel?.costValidation?.postValue(true)
                    } else {
                        viewModel?.costValidation?.postValue(true)
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

            viewModel?.addValidations?.observe(viewLifecycleOwner) {
                containerButtons.buttonAdd.isEnabled = it

                if (it) {
                    containerButtons.buttonAdd.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                } else {
                    containerButtons.buttonAdd.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
                }
            }

            containerButtons.buttonAdd.setOnClickListener {
                val lifeCost = LifeCost(0, containerDate.textDate.text.toString(), containerUser.textUser.toString(),
                    containerUser.icUser.id, containerMoney.icExpenditure.resources.toString().length, containerMoney.editTextMoney.text.toString().toInt(), containerRemarks.editTextRemarks.text.toString())
                listener?.onAdd(lifeCost)
                parentFragmentManager.popBackStack()
            }

            containerButtons.buttonCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
        return binding.root
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker().build().apply {
            addOnPositiveButtonClickListener { datetime ->
                binding.containerDate.textDate.text = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(datetime)
                viewModel?.dateValidation?.postValue(true)
            }
        }.show(childFragmentManager, "MaterialDatePicker")
    }

    private fun showSelectUserDialog(users: ArrayList<String>) {
        viewModel?.also {
            AlertDialog.Builder(requireContext())
                .setAdapter(ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, users)) { _, i ->
                    binding.containerUser.icUser.setColorFilter(ContextCompat.getColor(requireContext(), it.getUserResource(users[i])), PorterDuff.Mode.SRC_IN)
                    binding.containerUser.textUser.text = users[i]
                    viewModel?.userValidation?.postValue(true)
                }.show()
        }

    }

    fun setListener(listener: OnAddListener) {
        this.listener = listener
    }

}


package com.example.walletfortwo.view.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.R
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.databinding.FragmentGiveCostAddBinding
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.view.adapter.ListDialogAdapter
import com.example.walletfortwo.viewModel.AddFragmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

//お金を渡したリスト追加画面
class GiveCostAddFragment : Fragment(), ListDialogAdapter.OnSelectItemListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[AddFragmentViewModel::class.java]
        }
    }

    companion object {
        fun newInstance(): GiveCostAddFragment {
            val args = Bundle()
            val fragment = GiveCostAddFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnAddListener {
        fun onAdd(giveCost: GiveCost)
    }

    private lateinit var binding: FragmentGiveCostAddBinding
    private var listener: OnAddListener? = null
    private var fromUser: User? = null
    private var toUser: User? = null
    private var expenditureItem: ExpenditureItem? = null
    private var expenditureDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentGiveCostAddBinding.inflate(inflater, container, false).apply {
            viewModel?.also { vm ->
                containerDate.textDate.setOnClickListener {
                    showDatePicker()
                }

                containerDate.buttonToday.setOnClickListener {
                    val date = LocalDate.now()
                    val format = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    val toDay = date.format(format)

                    containerDate.textDate.text = toDay
                    vm.dateValidation.postValue(true)
                }

                containerFromUser.buttonUser.setOnClickListener {
                    val users = vm.getUserNames()
                    showSelectUserDialog(users)
                }

                containerToUser.buttonUser.visibility = View.GONE

                containerMoney.editTextMoney.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        if (containerMoney.editTextMoney.text.isNotEmpty()) {
                            vm.costValidation.postValue(true)
                        } else {
                            vm.costValidation.postValue(true)
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                })

                containerMoney.icExpenditure.setOnClickListener {
                    val expenditureItems = vm.getExpenditureItems()
                    showSelectExpenditureItemDialog(expenditureItems)
                }

                vm.addValidations.observe(viewLifecycleOwner) {
                    containerButtons.buttonAdd.isEnabled = it

                    if (it) {
                        containerButtons.buttonAdd.setBackgroundColor(ContextCompat.getColor(requireContext(), com.example.walletfortwo.R.color.purple_200))
                    } else {
                        containerButtons.buttonAdd.setBackgroundColor(ContextCompat.getColor(requireContext(), com.example.walletfortwo.R.color.gray))
                    }
                }

                containerButtons.buttonAdd.setOnClickListener {
                    val giveCost = GiveCost(0, containerDate.textDate.text.toString(), getName(fromUser?.name), getResource(fromUser?.color), getName(toUser?.name), getResource(toUser?.color),
                        getName(expenditureItem?.name), getResource(expenditureItem?.resource), containerMoney.editTextMoney.text.toString().toInt(), containerRemarks.editTextRemarks.text.toString())
                    listener?.onAdd(giveCost)
                    parentFragmentManager.popBackStack()
                }

                containerButtons.buttonCancel.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }
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
                .setAdapter(ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, users)) { _, i ->
                    fromUser = viewModel?.getUser(users[i])
                    toUser = viewModel?.getToUser(users[i])
                    binding.containerFromUser.icUser.setColorFilter(ContextCompat.getColor(requireContext(), getResource(fromUser?.color)), PorterDuff.Mode.SRC_IN)
                    binding.containerFromUser.textUser.text = getName(fromUser?.name)
                    binding.containerToUser.icUser.setColorFilter(ContextCompat.getColor(requireContext(), getResource(toUser?.color)), PorterDuff.Mode.SRC_IN)
                    binding.containerToUser.textUser.text = getName(toUser?.name)
                    viewModel?.userValidation?.postValue(true)
                }.show()
        }
    }

    private fun showSelectExpenditureItemDialog(expenditureItems: List<ExpenditureItem>) {
        val recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ListDialogAdapter(expenditureItems, this, viewLifecycleOwner)
        viewModel?.also {
            expenditureDialog = AlertDialog.Builder(requireContext())
                .setView(recyclerView).show()
        }

    }

    private fun getName(name: String?): String {
        return name ?: ""
    }

    private fun getResource(id: Int?): Int {
        return id ?: 0
    }

    fun setListener(listener: OnAddListener) {
        this.listener = listener
    }

    override fun onSelect(item: ExpenditureItem) {
        if (expenditureDialog != null) {
            expenditureItem = item
            binding.containerMoney.icExpenditure.setImageResource(item.resource)
            binding.containerMoney.icExpenditure.setColorFilter(ContextCompat.getColor(requireContext(), com.example.walletfortwo.R.color.purple_200), PorterDuff.Mode.SRC_IN)
            viewModel?.expenditureItemValidation?.postValue(true)
            expenditureDialog!!.dismiss()
            expenditureDialog = null
        }
    }
}
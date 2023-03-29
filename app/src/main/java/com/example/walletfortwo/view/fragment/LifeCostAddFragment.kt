package com.example.walletfortwo.view.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentLifeCostAddBinding
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.view.adapter.ListDialogAdapter
import com.example.walletfortwo.viewModel.AddFragmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

//生活費の追加画面
class LifeCostAddFragment : Fragment(), ListDialogAdapter.OnSelectItemListener {
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
    private var user: User? = null
    private var expenditureItem: ExpenditureItem? = null
    private var expenditureDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLifeCostAddBinding.inflate(inflater, container, false).apply {
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

                containerUser.buttonUser.setOnClickListener {
                    val users = vm.getUserNames()
                    showSelectUserDialog(users)
                }

                containerMoney.editTextMoney.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        if (containerMoney.editTextMoney.text.isNotEmpty()) {
                            vm.costValidation.postValue(true)
                        } else {
                            vm.costValidation.postValue(false)
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
                        containerButtons.buttonAdd.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    } else {
                        containerButtons.buttonAdd.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    }
                }

                containerButtons.buttonAdd.setOnClickListener {
                    val lifeCost = LifeCost(0, containerDate.textDate.text.toString(), getId(user?.id),
                        getName(expenditureItem?.name), getId(expenditureItem?.resource), containerMoney.editTextMoney.text.toString().toInt(), containerRemarks.editTextRemarks.text.toString())
                    listener?.onAdd(lifeCost)
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
                .setAdapter(ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, users)) { _, i ->
                    user = it.getUser(users[i])
                    binding.containerUser.icUser.setColorFilter(ContextCompat.getColor(requireContext(), getId(user?.color)), PorterDuff.Mode.SRC_IN)
                    binding.containerUser.textUser.text = getName(user?.name)
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

    private fun getId(id: Int?): Int {
        return id ?: Int.MAX_VALUE
    }

    fun setListener(listener: OnAddListener) {
        this.listener = listener
    }

    override fun onSelect(item: ExpenditureItem) {
        if (expenditureDialog != null) {
            expenditureItem = item
            binding.containerMoney.icExpenditure.setImageResource(item.resource)
            binding.containerMoney.icExpenditure.setColorFilter(ContextCompat.getColor(requireContext(), R.color.purple_200), PorterDuff.Mode.SRC_IN)
            viewModel?.expenditureItemValidation?.postValue(true)
            expenditureDialog!!.dismiss()
            expenditureDialog = null
        }
    }

}


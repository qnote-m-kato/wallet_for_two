package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentListBinding
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.view.adapter.LifeCostAdapter
import com.example.walletfortwo.viewModel.LifeCostViewModel

//生活費のリストの画面
class LifeCostFragment : Fragment(), LifeCostAdapter.OnSelectItemListener, LifeCostAddFragment.OnAddListener, SelectDateDialogFragment.OnSelectItemListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[LifeCostViewModel::class.java]
        }
    }
    private lateinit var binding: FragmentListBinding
    private lateinit var lifeCostAdapter: LifeCostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentListBinding.inflate(inflater, container, false).apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.also { vm ->
                vm.getFlag().observe(viewLifecycleOwner) {
                    lifeCostAdapter = LifeCostAdapter(vm.getList(), requireContext(), this@LifeCostFragment, resources, viewLifecycleOwner)
                    list.adapter = lifeCostAdapter
                    lifeCostAdapter.submitList(vm.getList())
                }
            }

            selectDate.setOnClickListener {
                val dialogFragment = SelectDateDialogFragment()
                dialogFragment.setListener(this@LifeCostFragment)
                dialogFragment.show(childFragmentManager, "")
            }

            addButton.setOnClickListener {
                val fragment = LifeCostAddFragment.newInstance().apply {
                    setListener(this@LifeCostFragment)
                }
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.replace(R.id.full_screen_fragment, fragment)?.commit()
            }
        }
        return binding.root
    }

    override fun onSelect(item: LifeCost) {
        AlertDialog.Builder(requireContext())
            .setTitle("この項目を削除")
            .setPositiveButton("OK") { _, _ ->
                viewModel?.delete(item)
            }
            .setNegativeButton("Cancel") { _, _ ->}
            .show()
    }

    override fun onAdd(lifeCost: LifeCost) {
        viewModel?.add(lifeCost)
    }

    override fun onSelectDate(year: Int, month: Int) {
        var date = getString(R.string.date_format_2).format(year, month)
        if (month == 0) {
            date = "全期間"
        }
        binding.selectDate.text = date

        lifeCostAdapter.submitList(viewModel?.searchDate(date)!!)
    }
}
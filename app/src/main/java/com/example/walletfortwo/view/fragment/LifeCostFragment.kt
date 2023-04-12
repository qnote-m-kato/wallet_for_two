package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.util.Log
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
class LifeCostFragment : Fragment(), LifeCostAdapter.OnSelectItemListener, LifeCostAddFragment.OnAddListener, FilterByDateDialogFragment.OnSelectItemListener, FilterByItemDialogFragment.OnReflectListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[LifeCostViewModel::class.java]
        }
    }
    private lateinit var binding: FragmentListBinding
    private lateinit var lifeCostAdapter: LifeCostAdapter
    private var checkList: List<Boolean> = mutableListOf(true, true, true, true, true, true, true, true)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentListBinding.inflate(inflater, container, false).apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.also { vm ->
                vm.getFlag().observe(viewLifecycleOwner) {
                    lifeCostAdapter = LifeCostAdapter(vm.getList(), requireContext(), this@LifeCostFragment, resources, viewLifecycleOwner)
                    list.adapter = lifeCostAdapter
                    lifeCostAdapter.submitList(vm.getList())
                    textSelectDate.text = "全期間"
                }
            }

            buttonSelectDate.setOnClickListener {
                val dialogFragment = FilterByDateDialogFragment()
                dialogFragment.setListener(this@LifeCostFragment)
                dialogFragment.show(childFragmentManager, "")
            }

            searchMenu.setOnClickListener {
                val dialogFragment = FilterByItemDialogFragment.newInstance(checkList)
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
        val date = if (month == 0) {
            "全期間"
        } else {
            getString(R.string.date_format_2).format(year, month)
        }
        binding.textSelectDate.text = date

        submitList(viewModel?.filterDate(date)!!)
    }

    override fun onReflect(checkList: List<Boolean>) {
        this.checkList = checkList
        submitList(viewModel?.filterItem(checkList, resources)!!)
    }

    private fun submitList(filterList: List<LifeCost>) {
        // CurrentListとNewListの内容が全く異なっているときに表示されるデータがおかしくなることがある
        // 元となるListを先に反映させることで正しい結果を表示できた
        lifeCostAdapter.submitList(viewModel?.getList()) {
            lifeCostAdapter.submitList(filterList)
        }
    }
}
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
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.view.adapter.GiveCostAdapter
import com.example.walletfortwo.viewModel.GiveCostViewModel

//お金を渡したリストの画面
class GiveCostFragment : Fragment(), GiveCostAdapter.OnSelectItemListener, GiveCostAddFragment.OnAddListener, FilterByDateDialogFragment.OnSelectItemListener, FilterByItemDialogFragment.OnReflectListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[GiveCostViewModel::class.java]
        }
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var giveCostAdapter: GiveCostAdapter
    private var checkList: List<Boolean> = mutableListOf(true, true, true, true, true, true, true, true)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false).apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.also { vm ->
                vm.getFlag().observe(viewLifecycleOwner) {
                    giveCostAdapter = GiveCostAdapter(vm.getList(), requireContext(), this@GiveCostFragment, resources, viewLifecycleOwner)
                    list.adapter = giveCostAdapter
                    giveCostAdapter.submitList(vm.getList())
                }
            }

            buttonSelectDate.setOnClickListener {
                val dialogFragment = FilterByDateDialogFragment()
                dialogFragment.setListener(this@GiveCostFragment)
                dialogFragment.show(childFragmentManager, "")
            }

            searchMenu.setOnClickListener {
                val dialogFragment = FilterByItemDialogFragment.newInstance(checkList)
                dialogFragment.setListener(this@GiveCostFragment)
                dialogFragment.show(childFragmentManager, "")
            }

            addButton.setOnClickListener {
                val fragment = GiveCostAddFragment.newInstance().apply {
                    setListener(this@GiveCostFragment)
                }
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.replace(R.id.full_screen_fragment, fragment)?.commit()
            }
        }
        return binding.root
    }

    override fun onSelect(item: GiveCost) {
        AlertDialog.Builder(requireContext())
            .setTitle("この項目を削除")
            .setPositiveButton("OK") { _, _ ->
                viewModel?.delete(item)
            }
            .setNegativeButton("Cancel") { _, _ ->}
            .show()
    }

    override fun onAdd(giveCost: GiveCost) {
        viewModel?.add(giveCost)
    }

    override fun onSelectDate(year: Int, month: Int) {
        var date = getString(R.string.date_format_2).format(year, month)
        if (month == 0) {
            date = "全期間"
        }
        binding.textSelectDate.text = date
        submitList(viewModel?.filterDate(date)!!)
    }

    override fun onReflect(checkList: List<Boolean>) {
        this.checkList = checkList
        submitList(viewModel?.filterItem(checkList, resources)!!)
    }

    private fun submitList(filterList: List<GiveCost>) {
        // CurrentListとNewListの内容が全く異なっているときに表示されるデータがおかしくなることがある
        // 元となるListを先に反映させることで正しい結果を表示できた
        giveCostAdapter.submitList(viewModel?.getList()) {
            giveCostAdapter.submitList(filterList)
        }
    }

}
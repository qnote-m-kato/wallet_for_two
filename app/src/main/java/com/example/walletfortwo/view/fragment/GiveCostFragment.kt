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
import com.example.walletfortwo.view.adapter.GiveCostAdapter
import com.example.walletfortwo.viewModel.GiveCostViewModel

//お金を渡したリストの画面
class GiveCostFragment : Fragment(), GiveCostAdapter.OnSelectItemListener, GiveCostAddFragment.OnAddListener, SelectDateDialogFragment.OnSelectItemListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[GiveCostViewModel::class.java]
        }
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var giveCostAdapter: GiveCostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false).apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.also { vm ->
                vm.getFlag().observe(viewLifecycleOwner) {
                    giveCostAdapter = GiveCostAdapter(vm.getList(), requireContext(), this@GiveCostFragment, resources, viewLifecycleOwner)
                    list.adapter = giveCostAdapter
                    giveCostAdapter.submitList(vm.searchDate(textSelectDate.text.toString()))
                }
            }

            buttonSelectDate.setOnClickListener {
                val dialogFragment = SelectDateDialogFragment()
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
        giveCostAdapter.submitList(viewModel?.searchDate(date)!!)
    }
}
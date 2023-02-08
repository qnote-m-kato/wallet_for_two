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
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.view.adapter.GiveCostAdapter
import com.example.walletfortwo.view.adapter.LifeCostAdapter
import com.example.walletfortwo.viewModel.GiveCostViewModel

//お金を渡したリストの画面
class GiveCostFragment : Fragment(), GiveCostAdapter.OnSelectItemListener, GiveCostAddFragment.OnAddListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[GiveCostViewModel::class.java]
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentListBinding.inflate(inflater, container, false).apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.also { vm ->
                vm.getFlag().observe(viewLifecycleOwner) {
                    list.adapter = GiveCostAdapter(vm.getList(), requireContext(), this@GiveCostFragment, resources, viewLifecycleOwner)
                }
            }
            addButton.setOnClickListener {
                val fragment = GiveCostAddFragment.newInstance().apply {
                    setListener(this@GiveCostFragment)
                }
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.replace(R.id.full_screen_fragment, fragment)?.commit()
            }
        }.root
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
}
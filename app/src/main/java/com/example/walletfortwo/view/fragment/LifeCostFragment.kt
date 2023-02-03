package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentListBinding
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.view.adapter.LifeCostAdapter
import com.example.walletfortwo.viewModel.LifeCostViewModel

//生活費のリストの画面
class LifeCostFragment : Fragment(), LifeCostAdapter.OnSelectItemListener, LifeCostAddFragment.OnAddListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[LifeCostViewModel::class.java]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentListBinding.inflate(inflater, container, false).apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.also { vm ->
                vm.getFlag().observe(viewLifecycleOwner) {
                    list.adapter = LifeCostAdapter(vm.getList(), requireContext(), this@LifeCostFragment, viewLifecycleOwner)
                }
            }
            addButton.setOnClickListener {
                val fragment = LifeCostAddFragment.newInstance().apply {
                    setListener(this@LifeCostFragment)
                }
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.replace(R.id.full_screen_fragment, fragment)?.commit()
            }
        }.root
    }

    override fun onSelect(item: LifeCost) {
        TODO("編集画面へ遷移")
    }

    override fun onAdd(lifeCost: LifeCost) {
        viewModel?.add(lifeCost)
    }

}
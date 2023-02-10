package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentUserDetailBinding
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.view.adapter.UserDetailAdapter
import com.example.walletfortwo.viewModel.UserDetailViewModel
import com.example.walletfortwo.viewModel.viewModelFactory.UserDetailViewModelFactory

//人の詳細画面
class UserDetailFragment : Fragment() {
    private val args: UserDetailFragmentArgs by navArgs()
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this, UserDetailViewModelFactory(it, args.userName, resources))[UserDetailViewModel::class.java]
        }
    }
    private lateinit var binding: FragmentUserDetailBinding
    private var adapterType: Int = 0
    private lateinit var lifeCostAdapter: UserDetailAdapter<LifeCost>
    private lateinit var giveCostFromAdapter: UserDetailAdapter<GiveCost>
    private lateinit var giveCostToAdapter: UserDetailAdapter<GiveCost>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentUserDetailBinding.inflate(inflater, container, false).apply {
            viewModel?.also { vm ->
                vm.getUserDetail().observe(viewLifecycleOwner) {
                    icUser.setColorFilter(ContextCompat.getColor(requireContext(), it.user.color))
                    textYen.text = getString(R.string.cost_format).format(it.totalCost)
                    lifeCostAdapter = UserDetailAdapter(it.lifeCosts, true, requireContext(), resources, viewLifecycleOwner)
                    giveCostFromAdapter = UserDetailAdapter(it.giveCostsFrom, false, requireContext(), resources, viewLifecycleOwner)
                    giveCostToAdapter = UserDetailAdapter(it.giveCostsTo, false, requireContext(), resources, viewLifecycleOwner)

                    list.adapter = when (adapterType) {
                        0 -> {
                            lifeCostAdapter.notifyDataSetChanged()
                            lifeCostAdapter
                        }
                        1 -> {
                            giveCostFromAdapter.notifyDataSetChanged()
                            giveCostFromAdapter
                        }
                        else -> {
                            giveCostToAdapter.notifyDataSetChanged()
                            giveCostToAdapter
                        }
                    }
                }
            }
            list.layoutManager = LinearLayoutManager(requireContext())
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            containerLifeCost.setOnClickListener {
                setUnderLineColor(R.color.purple_200, R.color.light_gray, R.color.light_gray)
                if (adapterType != 0) {
                    adapterType = 0
                    list.adapter = lifeCostAdapter
                    lifeCostAdapter.notifyDataSetChanged()
                }
            }
            containerGiveFrom.setOnClickListener {
                setUnderLineColor(R.color.light_gray, R.color.purple_200, R.color.light_gray)
                if (adapterType != 1) {
                    adapterType = 1
                    list.adapter = giveCostFromAdapter
                    giveCostFromAdapter.notifyDataSetChanged()
                }
            }
            containerGiveTo.setOnClickListener {
                setUnderLineColor(R.color.light_gray, R.color.light_gray, R.color.purple_200)
                if (adapterType != 2) {
                    adapterType = 2
                    list.adapter = giveCostToAdapter
                    giveCostToAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setUnderLineColor(life: Int, giveFrom: Int, giveTo: Int) {
        binding.lifeCostUnderLine.setBackgroundColor(ContextCompat.getColor(requireContext(), life))
        binding.giveFromUnderLine.setBackgroundColor(ContextCompat.getColor(requireContext(), giveFrom))
        binding.giveToUnderLine.setBackgroundColor(ContextCompat.getColor(requireContext(), giveTo))

    }
}
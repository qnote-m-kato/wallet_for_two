package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.walletfortwo.databinding.FragmentHomeBinding
import com.example.walletfortwo.viewModel.AddFragmentViewModel
import com.example.walletfortwo.viewModel.HomeViewModel

//ホーム画面
class HomeFragment : Fragment() {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[HomeViewModel::class.java]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel?.also { vm ->
                vm.getUserDetails().observe(viewLifecycleOwner) {
                    if (it.size == 2) {
                        val userA = it[0]
                        userAContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), userA.user.color))
                        userAContainer.textName.text = userA.user.name
                        userAContainer.textYen.text = userA.totalCost.toString()

                        val userB = it[1]
                        userBContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), userB.user.color))
                        userBContainer.textName.text = userB.user.name
                        userBContainer.textYen.text = userB.totalCost.toString()
                        Log.i("myu", "home observe success")
                    } else {
                        Log.i("myu", "home observe error")
                    }

                }
                userAContainer.container.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.homeToUserDetail())
                }

                userBContainer.container.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.homeToUserDetail())
                }
            }

        }.root
    }

    override fun onResume() {
        super.onResume()
        viewModel?.updateUserDetails()
    }
}
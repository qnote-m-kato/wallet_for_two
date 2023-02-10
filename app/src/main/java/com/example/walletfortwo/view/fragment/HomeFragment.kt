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
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentHomeBinding
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.UserDetail
import com.example.walletfortwo.model.repository.UserDetailRepository
import com.example.walletfortwo.viewModel.AddFragmentViewModel
import com.example.walletfortwo.viewModel.HomeViewModel

//ホーム画面
class HomeFragment : Fragment() {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[HomeViewModel::class.java]
        }
    }
    private lateinit var userA: UserDetail
    private lateinit var userB: UserDetail

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel?.also { vm ->
                UserDetailRepository.getUpdate().observe(viewLifecycleOwner) {
                    vm.update()
                    Log.i("myu", "update")
                }
                vm.getUserDetails().observe(viewLifecycleOwner) {
                    if (it.size >= 2) {
                        userA = it[0]
                        userAContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), userA.user.color))
                        userAContainer.textName.text = userA.user.name
                        userAContainer.textYen.text = getString(R.string.cost_format).format(userA.totalCost)

                        userB = it[1]
                        userBContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), userB.user.color))
                        userBContainer.textName.text = userB.user.name
                        userBContainer.textYen.text = getString(R.string.cost_format).format(userB.totalCost)
                        Log.i("myu", "getUserDetails")
                    }
                    Log.i("myu", it.size.toString())

                }
                userAContainer.container.setOnClickListener {
                    if (::userA.isInitialized) {
                        findNavController().navigate(HomeFragmentDirections.homeToUserDetail(userA.user.name))
                    }
                }

                userBContainer.container.setOnClickListener {
                    if (::userB.isInitialized) {
                        findNavController().navigate(HomeFragmentDirections.homeToUserDetail(userB.user.name))
                    }
                }
            }

        }.root
    }

    override fun onResume() {
        super.onResume()
        viewModel?.update()
    }
}
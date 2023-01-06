package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.walletfortwo.databinding.FragmentHomeBinding

//ホーム画面
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).apply {
            userA.container.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.homeToUserDetail())
            }

            userB.container.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.homeToUserDetail())
            }
        }.root
    }
}
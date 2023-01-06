package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.walletfortwo.databinding.FragmentListBinding

//お金を渡したリストの画面
class GiveCostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentListBinding.inflate(inflater, container, false).apply {
            addButton.setOnClickListener {
                findNavController().navigate(GiveCostFragmentDirections.giveCostToGiveCostAdd())
            }
        }.root
    }
}
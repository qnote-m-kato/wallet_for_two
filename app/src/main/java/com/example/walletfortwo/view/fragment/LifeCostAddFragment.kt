package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.walletfortwo.databinding.FragmentLifeCostAddBinding

//生活費の追加画面
class LifeCostAddFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentLifeCostAddBinding.inflate(inflater, container, false).apply {
            containerButtons.buttonCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }.root
    }
}
package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.walletfortwo.databinding.FragmentLifeCostBinding

//生活費のリストの画面
class LifeCostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentLifeCostBinding.inflate(inflater, container, false).apply {

        }.root
    }
}
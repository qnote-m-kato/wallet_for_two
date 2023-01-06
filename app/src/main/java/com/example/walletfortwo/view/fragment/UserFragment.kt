package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.walletfortwo.databinding.FragmentUserBinding

//人の詳細画面
class UserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentUserBinding.inflate(inflater, container, false).apply {

        }.root
    }
}
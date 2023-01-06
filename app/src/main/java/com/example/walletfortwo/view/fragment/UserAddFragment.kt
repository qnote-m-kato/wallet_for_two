package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.walletfortwo.databinding.FragmentUserAddBinding

//人の追加画面
class UserAddFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentUserAddBinding.inflate(inflater, container, false).apply {

        }.root
    }
}
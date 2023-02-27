package com.example.walletfortwo.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletfortwo.R
import com.example.walletfortwo.databinding.FragmentHomeBinding
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.UserDetail
import com.example.walletfortwo.model.repository.UserDetailRepository
import com.example.walletfortwo.view.adapter.GridDialogAdapter
import com.example.walletfortwo.view.adapter.ListDialogAdapter
import com.example.walletfortwo.viewModel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//ホーム画面
class HomeFragment : Fragment(), GridDialogAdapter.OnSelectItemListener {
    private val viewModel by lazy {
        activity?.application?.let {
            ViewModelProvider(this)[HomeViewModel::class.java]
        }
    }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userA: UserDetail
    private lateinit var userB: UserDetail
    private var userAColor: Int = 0
    private var userBColor: Int = 0
    private var dialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel?.also { vm ->
                UserDetailRepository.getUpdate().observe(viewLifecycleOwner) {
                    vm.update()
                }
                vm.getUserDetails().observe(viewLifecycleOwner) {
                    if (it.size >= 2) {
                        userA = it[0]
                        userAContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), userA.user.color))
                        userAColor = userA.user.color
                        userAContainer.textName.text = userA.user.name
                        userAContainer.textYen.text = getString(R.string.cost_format).format(userA.totalCost)

                        userB = it[1]
                        userBContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), userB.user.color))
                        userBColor = userB.user.color
                        userBContainer.textName.text = userB.user.name
                        userBContainer.textYen.text = getString(R.string.cost_format).format(userB.totalCost)
                    }
                }

                buttonSave.visibility = View.GONE

                userAContainer.icUserShadow.visibility = View.GONE
                userAContainer.editTextName.visibility = View.GONE
                userAContainer.viewName.visibility = View.GONE
                userAContainer.icUser.isEnabled = false
                userAContainer.container.isEnabled = true

                userBContainer.icUserShadow.visibility = View.GONE
                userBContainer.editTextName.visibility = View.GONE
                userBContainer.viewName.visibility = View.GONE
                userBContainer.icUser.isEnabled = false
                userBContainer.container.isEnabled = true

            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editUser()
    }

    private fun editUser() {
        binding.apply {
            buttonEdit.setOnClickListener {
                setOnClickListener()
                changeEditView(true)

                userAContainer.editTextName.hint = userA.user.name
                userBContainer.editTextName.hint = userB.user.name
            }

            buttonSave.setOnClickListener {

                setOnClickListener()
                changeEditView(false)

                val nameA = if (userAContainer.editTextName.text.isNotEmpty()) {
                    userAContainer.editTextName.text.toString()
                } else {
                    userAContainer.textName.text.toString()
                }

                val nameB = if (userBContainer.editTextName.text.isNotEmpty()) {
                    userBContainer.editTextName.text.toString()
                } else {
                    userBContainer.textName.text.toString()
                }

                viewModel?.editUser(0, nameA, userAColor, 1, nameB, userBColor)

            }
        }
    }

    private fun changeEditView(isEdit: Boolean) {
        val viewState1 = if (isEdit) View.GONE else View.VISIBLE
        val viewState2 = if (isEdit) View.VISIBLE else View.GONE
        binding.apply {
            buttonEdit.visibility = viewState1
            buttonSave.visibility = viewState2

            userAContainer.textName.visibility = viewState1
            userBContainer.textName.visibility = viewState1

            userAContainer.editTextName.visibility = viewState2
            userBContainer.editTextName.visibility = viewState2

            userAContainer.icUserShadow.visibility = viewState2
            userBContainer.icUserShadow.visibility = viewState2

            userAContainer.viewName.visibility = viewState2
            userBContainer.viewName.visibility = viewState2

            userAContainer.icUser.isEnabled = isEdit
            userBContainer.icUser.isEnabled = isEdit

            userAContainer.container.isEnabled = !isEdit
            userBContainer.container.isEnabled = !isEdit
        }
    }

    private fun setOnClickListener() {
        binding.apply {
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

            userAContainer.icUser.setOnClickListener {
                showSelectColorDialog(0, userBColor)
            }

            userBContainer.icUser.setOnClickListener {
                showSelectColorDialog(1, userAColor)
            }
        }
    }

    private fun showSelectColorDialog(id: Int, color: Int) {
        val recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerView.adapter = GridDialogAdapter(viewModel?.colorList!!, id, color, requireContext(), this, viewLifecycleOwner)
        viewModel?.also {
            dialog = AlertDialog.Builder(requireContext())
                .setView(recyclerView).show()
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel?.update()
    }

    override fun onSelect(id: Int, item: Int) {
        if (dialog != null) {
            if (id == 0) {
                binding.userAContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), item))
                userAColor = item
            } else {
                binding.userBContainer.icUser.setColorFilter(ContextCompat.getColor(requireContext(), item))
                userBColor = item
            }
            dialog!!.dismiss()
            dialog = null
        }
    }
}
package com.example.logincardview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.MainActivity
import com.example.logincardview.R
import com.example.logincardview.controller.LocalController
import com.example.logincardview.databinding.FragmentLocalBinding

class LocalFragment() : Fragment(R.layout.fragment_local) {

    private lateinit var bindingFragment: FragmentLocalBinding
    private lateinit var localController: LocalController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingFragment = FragmentLocalBinding.inflate(inflater, container, false)

        initRecyclerView()

        localController = LocalController(requireActivity() as MainActivity, this)

        return bindingFragment.root
    }

    private fun initRecyclerView() {
        bindingFragment.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())
    }

    fun getLocalFragmentBinding(): FragmentLocalBinding {
        return this.bindingFragment
    }
}







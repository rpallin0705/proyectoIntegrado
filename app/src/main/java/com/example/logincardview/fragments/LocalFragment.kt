package com.example.logincardview.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.MainScreenActivity
import com.example.logincardview.R
import com.example.logincardview.adapter.AdapterLocal
import com.example.logincardview.controller.Controller
import com.example.logincardview.databinding.FragmentLocalBinding
import com.example.logincardview.models.Local
import com.example.logincardview.models.LocalRepository

class LocalFragment() : Fragment(R.layout.fragment_local) {

    private lateinit var bindingFragment: FragmentLocalBinding
    private lateinit var controller: Controller

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingFragment = FragmentLocalBinding.inflate(inflater, container, false)

        initRecyclerView()

        controller = Controller(requireActivity() as MainScreenActivity, this)

        return bindingFragment.root
    }

    private fun initRecyclerView() {
        bindingFragment.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())
    }

    fun getLocalFragmentBinding(): FragmentLocalBinding {
        return this.bindingFragment
    }
}







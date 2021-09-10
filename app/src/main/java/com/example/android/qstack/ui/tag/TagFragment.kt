package com.example.android.qstack.ui.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.qstack.databinding.FragmentTagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagFragment : Fragment() {

    private lateinit var binding: FragmentTagBinding
    private val tagViewModel: TagViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTagBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.tagViewModel = tagViewModel



        return binding.root
    }

}
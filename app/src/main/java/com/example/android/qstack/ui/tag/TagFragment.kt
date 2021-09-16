package com.example.android.qstack.ui.tag

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.qstack.DetailActivity
import com.example.android.qstack.R
import com.example.android.qstack.databinding.FragmentTagBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

const val DETAIL = "details"

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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tag_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.confirm_menu) {
            val itemChosen = getAllCheckedChips()
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DETAIL, itemChosen)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun getAllCheckedChips(): String {
        val chipGroup = binding.chipGroup
        val childrenId = chipGroup.checkedChipIds
        val stringBuilder = StringBuilder()
        for ((index, id) in childrenId.withIndex()) {
            val chipView = chipGroup.findViewById<Chip>(id)
            if (index == childrenId.size.minus(1)) {
                stringBuilder.append(chipView.tag.toString())
            } else {
                stringBuilder.append(chipView.tag.toString())
                stringBuilder.append(";")
            }
        }
        return stringBuilder.toString()
    }
}
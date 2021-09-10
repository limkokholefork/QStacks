package com.example.android.qstack.utils

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.example.android.qstack.R
import com.example.android.qstack.model.TagResponse
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import timber.log.Timber


@BindingAdapter("setChipDisplay")
fun ChipGroup.bindTagToChips(tagResponse: TagResponse?){
    val tags: List<String?>? = tagResponse?.items?.map {
        it.name
    }

    if (tags != null) {
        for (tag in tags){
            val chipView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_chip, this, false) as Chip
            chipView.id = View.generateViewId()
            chipView.tag = tag
            chipView.text = tag
            chipView.setOnCheckedChangeListener { compoundButton, checked ->
                if (checked){
                    if (this.checkedChipIds.size >= 5){
                        for (chip in this.children){
                            val singleChip = chip as Chip
                            singleChip.isEnabled = singleChip.isChecked
                        }
                    }
                }else{
                    if (checkedChipIds.size < 5){
                        for (chip in this.children){
                            val singleChip = chip as Chip
                            singleChip.isEnabled = true
                        }
                    }
                }
            }
            this.addView(chipView)
        }
    }
}

//@InverseBindingAdapter(attribute = "", event = "")
//fun ChipGroup.getCheckedChipNumber(tagResponse: TagResponse?): Int{
//    val checkedChipIds = this.checkedChipIds
//    return checkedChipIds.size
//}
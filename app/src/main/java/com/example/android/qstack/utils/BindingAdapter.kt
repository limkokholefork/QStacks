package com.example.android.qstack.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.qstack.R
import com.example.android.qstack.model.TagItem
import com.example.android.qstack.model.UserItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


@BindingAdapter("setChipDisplay")
fun ChipGroup.bindTagToChips(tagResponse: List<TagItem>?) {
    val tags: List<String>? = tagResponse?.map {
        it.name
    }
    if (tags != null) {
        for (tag in tags) {
            val chipView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_chip, this, false) as Chip
            chipView.id = View.generateViewId()
            chipView.tag = tag
            chipView.text = tag
            chipView.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    if (this.checkedChipIds.size >= 4) {
                        for (chip in this.children) {
                            val singleChip = chip as Chip
                            singleChip.isEnabled = singleChip.isChecked
                        }
                    }
                } else {
                    if (checkedChipIds.size < 4) {
                        for (chip in this.children) {
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

@BindingAdapter("progressVisibility")
fun ProgressBar.loadingVisibility(tagListItem: List<TagItem>?) {
    if (tagListItem?.isNotEmpty() == true) {
        this.visibility = View.GONE
    } else this.visibility = View.VISIBLE
}

@BindingAdapter("bindImage")
fun ImageView.bindImageToUserView(userItem: UserItem?) {
    Glide.with(this)
        .load(userItem?.profileImage)
        .placeholder(R.drawable.place_holder)
        .into(this)
}

@BindingAdapter("hideAbout")
fun TextView.hideAbout(userItem: UserItem?) {
    visibility = if (userItem?.aboutMe.isNullOrBlank()){
        View.GONE
    }else View.VISIBLE
}




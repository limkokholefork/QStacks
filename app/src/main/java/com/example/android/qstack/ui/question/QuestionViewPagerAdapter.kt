package com.example.android.qstack.ui.question

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuestionViewPagerAdapter(fragment: Fragment,
                               private val arrayList: ArrayList<Fragment>)
    : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun createFragment(position: Int): Fragment {
        return arrayList[position]
    }
}
package com.example.android.qstack.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.qstack.databinding.QuestionViewpagerBinding
import com.example.android.qstack.ui.question.featuredQuestions.FeaturedQuestionFragment
import com.example.android.qstack.ui.question.newQuestions.NewQuestionsFragment
import com.example.android.qstack.ui.question.unansweredQuestion.UnansweredQuestionFragment
import com.google.android.material.tabs.TabLayoutMediator

class QuestionViewPager : Fragment() {

    private lateinit var binding: QuestionViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout
        binding = QuestionViewpagerBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(NewQuestionsFragment(),
            FeaturedQuestionFragment(), UnansweredQuestionFragment())
        binding.viewpager.adapter = QuestionViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "NEWEST"
                1 -> tab.text = "FEATURED"
                2 -> tab.text = "UNANSWERED"
            }
        }.attach()

        return binding.root
    }
}
package com.example.android.qstack.ui.question.featuredQuestions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.qstack.databinding.QuestionItemBinding
import com.example.android.qstack.model.FeaturedQuestion
import com.example.android.qstack.utils.convertEpochDateToTime

class FeaturedQuestionAdapter(private val listener: FeaturedQuestionListener) :
    PagingDataAdapter<FeaturedQuestion,
            FeaturedQuestionAdapter.FeaturedQuestionViewHolder>(FeaturedQuestionViewHolder.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedQuestionViewHolder {
        return FeaturedQuestionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FeaturedQuestionViewHolder, position: Int) {
        val featuredQuestion = getItem(position)
        holder.bind(featuredQuestion)
        holder.itemView.setOnClickListener {
            listener.clickListener(featuredQuestion)
        }
    }


    class FeaturedQuestionViewHolder(val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: FeaturedQuestion?) {
            binding.profilePics.bindImageToView(question?.owner?.profileImage)
            binding.answers.text = "${question?.answerCount?.toString() ?: (-1).toString()}answers"
            binding.views.text = "${question?.bountyAmount?.toString() ?: (-1).toString()}bounty"
            binding.votes.text = "${question?.score?.toString() ?: (-1).toString()}votes"
            binding.textViewName.text = question?.owner?.displayName
            binding.textViewTitle.text = question?.title?.let {
                HtmlCompat.fromHtml(
                    it,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
            }
            binding.textViewTime.text = question?.creationDate?.convertEpochDateToTime()
            binding.questionBody.text =
                question?.questionBody?.let {
                    HtmlCompat.fromHtml(
                        it,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                }
            if (question?.tags != null) createTags(question.tags, question)
        }

        private fun createTags(tags: List<String?>, question: FeaturedQuestion?) {
            when {
                tags.size >= 3 -> {
                    binding.tagOne.text = question?.tags?.get(0)
                    binding.tagTwo.text = question?.tags?.get(1)
                    binding.tagThree.text = question?.tags?.get(2)
                }
                tags.size == 2 -> {
                    binding.tagOne.text = question?.tags?.get(0)
                    binding.tagTwo.text = question?.tags?.get(1)
                    binding.tagThree.isVisible = false
                }
                tags.size == 1 -> {
                    binding.tagOne.text = question?.tags?.get(0)
                    binding.tagTwo.isVisible = false
                    binding.tagThree.isVisible = false
                }
                else -> {
                    binding.tagOne.isVisible = false
                    binding.tagTwo.isVisible = false
                    binding.tagThree.isVisible = false
                }
            }
        }



        private fun ImageView.bindImageToView(profilePicsUrl: String?) {
            Glide.with(this).load(profilePicsUrl).into(this)
        }

        companion object {
            fun from(parent: ViewGroup): FeaturedQuestionViewHolder {
                return FeaturedQuestionViewHolder(
                    QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            val diffUtil = object : DiffUtil.ItemCallback<FeaturedQuestion>() {
                override fun areItemsTheSame(
                    oldItem: FeaturedQuestion,
                    newItem: FeaturedQuestion
                ): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: FeaturedQuestion,
                    newItem: FeaturedQuestion
                ): Boolean {
                    return oldItem.questionId == newItem.questionId
                }

            }
        }
    }
}

class FeaturedQuestionListener(private val listener: (questionLink: String?)-> Unit){
    fun clickListener(question: FeaturedQuestion?) = listener(question?.link)
}
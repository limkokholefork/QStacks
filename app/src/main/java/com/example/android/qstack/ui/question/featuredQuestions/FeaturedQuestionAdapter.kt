package com.example.android.qstack.ui.question.featuredQuestions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
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
            binding.textViewTitle.text = question?.title
            binding.textViewTime.text = question?.creationDate?.convertEpochDateToTime()
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
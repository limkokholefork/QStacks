package com.example.android.qstack.ui.question.unansweredQuestion

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.qstack.databinding.QuestionItemBinding
import com.example.android.qstack.model.UnansweredQuestion
import com.example.android.qstack.utils.convertEpochDateToTime

class UnansweredQuestionAdapter(private val clickListener: ClickListener) :
    PagingDataAdapter<UnansweredQuestion,
            UnansweredQuestionAdapter.UnansweredQuestionViewHolder>(UnansweredQuestionViewHolder.diffUtil) {

    override fun onBindViewHolder(holder: UnansweredQuestionViewHolder, position: Int) {
        val unansweredQuestion = getItem(position)
        holder.bind(unansweredQuestion)
        holder.itemView.setOnClickListener {
            clickListener.clickListen(unansweredQuestion)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UnansweredQuestionViewHolder {
        return UnansweredQuestionViewHolder.from(parent)
    }


    class UnansweredQuestionViewHolder(val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: UnansweredQuestion?) {
            binding.profilePics.bindImageToView(question?.owner?.profileImage)
            binding.answers.text = "${question?.answerCount?.toString() ?: (-1).toString()}answers"
            binding.views.text = "${question?.viewCount?.toString() ?: (-1).toString()}views"
            binding.votes.text = "${question?.score?.toString() ?: (-1).toString()}votes"
            binding.textViewName.text = question?.owner?.displayName
            binding.textViewTitle.text = question?.title
            binding.textViewTime.text = question?.creationDate?.convertEpochDateToTime()
        }

        private fun ImageView.bindImageToView(profilePicsUrl: String?) {
            Glide.with(this).load(profilePicsUrl).into(this)
        }

        companion object {
            fun from(parent: ViewGroup): UnansweredQuestionViewHolder {
                return UnansweredQuestionViewHolder(
                    QuestionItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            val diffUtil = object : DiffUtil.ItemCallback<UnansweredQuestion>() {
                override fun areItemsTheSame(
                    oldItem: UnansweredQuestion,
                    newItem: UnansweredQuestion
                ): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: UnansweredQuestion,
                    newItem: UnansweredQuestion
                ): Boolean {
                    return oldItem.questionId == newItem.questionId
                }
            }
        }
    }
}

class ClickListener(private val question: (link: String?) -> Unit) {
    fun clickListen(unansweredQuestion: UnansweredQuestion?) =
        question(unansweredQuestion?.questionLink)
}
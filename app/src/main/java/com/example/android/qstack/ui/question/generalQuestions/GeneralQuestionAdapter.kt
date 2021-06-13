package com.example.android.qstack.ui.question.generalQuestions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.qstack.databinding.QuestionItemBinding
import com.example.android.qstack.model.Questions
import com.example.android.qstack.utils.convertEpochDateToTime

class GeneralQuestionAdapter(private val listener: WebViewClickListener)
    : PagingDataAdapter<Questions,
        GeneralQuestionAdapter.NewQuestionViewHolder>(NewQuestionViewHolder.newQuestionUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewQuestionViewHolder {
        return NewQuestionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewQuestionViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
        holder.itemView.setOnClickListener {
            listener.onQuestionClick(question)
        }
    }

    class NewQuestionViewHolder(private val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(question: Questions?) {
            binding.profilePics.bindImageToView(question?.owner?.profileImage)
            binding.answers.text = "${question?.answerCount?.toString() ?: (-1).toString()}answers"
            binding.views.text = "${question?.viewCount?.toString() ?: (-1).toString()}views"
            binding.votes.text = "${question?.score?.toString() ?: (-1).toString()}votes"
            binding.textViewName.text = question?.owner?.displayName
            binding.textViewTitle.text = question?.title
            binding.textViewTime.text = question?.creationDate?.convertEpochDateToTime()
        }

        private fun ImageView.bindImageToView(profilePicsUrl : String?){
            Glide.with(this).load(profilePicsUrl).into(this)
        }

        companion object{
            fun from(parent : ViewGroup) : NewQuestionViewHolder {
                return NewQuestionViewHolder(QuestionItemBinding.
                inflate(LayoutInflater.from(parent.context), parent, false))
            }

            val newQuestionUtil = object : DiffUtil.ItemCallback<Questions>() {
                override fun areItemsTheSame(oldItem: Questions, newItem: Questions): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: Questions, newItem: Questions): Boolean {
                    return oldItem.questionId == newItem.questionId
                }
            }
        }

    }
}

class WebViewClickListener(private val launchWebView : (questionLink : String?)-> Unit){
    fun onQuestionClick(question: Questions?) = launchWebView(question?.questionLink)
}
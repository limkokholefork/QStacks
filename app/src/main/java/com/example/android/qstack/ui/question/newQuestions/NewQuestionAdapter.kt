package com.example.android.qstack.ui.question.newQuestions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.qstack.databinding.QuestionItemBinding
import com.example.android.qstack.model.NewQuestion
import com.example.android.qstack.utils.convertEpochDateToTime

class NewQuestionAdapter(private val listener: NewQuestionListener) : PagingDataAdapter<NewQuestion,
        NewQuestionAdapter.NewQuestionViewHolder>(NewQuestionViewHolder.newQuestionUtil) {


    override fun onBindViewHolder(holder: NewQuestionViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.setNewQuestionClickListener(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewQuestionViewHolder {
        return NewQuestionViewHolder.from(parent)
    }


    class NewQuestionViewHolder(private val binding: QuestionItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(question: NewQuestion?) {
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
                return NewQuestionViewHolder(
                    QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            val newQuestionUtil = object : DiffUtil.ItemCallback<NewQuestion>() {
                override fun areItemsTheSame(oldItem: NewQuestion, newItem: NewQuestion): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: NewQuestion, newItem: NewQuestion): Boolean {
                    return oldItem.questionId == newItem.questionId
                }
            }
        }

    }
}

class NewQuestionListener(private val webViewActivity : (link : String?)-> Unit){
    fun setNewQuestionClickListener(newQuestion: NewQuestion?) = webViewActivity(newQuestion?.link)
}
package com.example.android.qstack.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.qstack.databinding.ItemUserBinding
import com.example.android.qstack.model.UserItem

class UserAdapter(private val listener: UserClickItemListener):
    PagingDataAdapter<UserItem, UserAdapter.UserViewHolder>(UserViewHolder.diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.userItem = getItem(position)
        holder.binding.userClickListener = listener
    }

    class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                return UserViewHolder(
                    ItemUserBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }

            val diffUtil = object: DiffUtil.ItemCallback<UserItem>(){
                override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                    return oldItem.userId == newItem.userId
                }

            }
        }
    }
}

class UserClickItemListener(val mListener: (link: String?)->Unit){
    fun getUSerIdOnClick(userItem: UserItem?) = mListener(userItem?.link)
}
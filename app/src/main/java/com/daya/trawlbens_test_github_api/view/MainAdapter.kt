package com.daya.trawlbens_test_github_api.view

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daya.trawlbens_test_github_api.data.User
import com.daya.trawlbens_test_github_api.databinding.ItemUserBinding

class MainAdapter : ListAdapter<User,MainAdapter.MainViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class MainViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            Glide.with(binding.root)
                .load(item.avatar_url)
                .into(binding.imgProfile)

            binding.tvName.text = item.login

            //lazy load
            if (item.isExpanded) {
                additionalItemVisible(true)

                binding.tvAccount.text = "@${item.twitterUserName}}"
                binding.tvEmail.text = item.email
                binding.tvLocation.text = item.location

                val followInformation = SpannableStringBuilder()
                    .bold {
                        append(item.followers.toString())
                    }
                    .append(" followers\t")
                    .append("\t\\u00b7 ")
                    .bold {
                        append(item.following.toString())
                    }
                    .append("following")

                binding.tvFollowInfo.text = followInformation
            } else {
                additionalItemVisible(false)
            }

        }


        private fun additionalItemVisible(visible: Boolean = false) {
            binding.tvAccount.isVisible = visible
            binding.tvEmail.isVisible = visible
            binding.tvLocation.isVisible = visible
            binding.tvFollowInfo.isVisible = visible
            binding.imgEmail.isVisible = visible
            binding.imgLocation.isVisible = visible
            binding.imgPeople.isVisible = visible
        }
    }

    companion object{
        private val diffUtil = object :DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
        }
    }
}
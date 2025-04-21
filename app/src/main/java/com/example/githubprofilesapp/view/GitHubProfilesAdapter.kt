package com.example.githubprofilesapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprofilesapp.R
import com.example.githubprofilesapp.databinding.GithubProfilesItemBinding
import com.example.githubprofilesapp.model.GHRepo

class GitHubProfilesAdapter(private val mContext: Context, private var repoList:List<GHRepo>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<GitHubProfilesAdapter.ProfilesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
       val binding = GithubProfilesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProfilesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return repoList.size
    }
    fun updateList(newList: List<GHRepo>) {
        repoList = newList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        with(holder.binding) {
            val repo: GHRepo = repoList[position]
            gitProfileId.text =
                mContext.resources.getString(R.string.repo_concat) + repo.id.toString()
            gitProfileName.text =
                mContext.resources.getString(R.string.repo_concat) + repo.name
            holder.itemView.setOnClickListener {
                onItemClick(repo.html_url)
            }
        }

    }

    inner class ProfilesViewHolder(val binding:GithubProfilesItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
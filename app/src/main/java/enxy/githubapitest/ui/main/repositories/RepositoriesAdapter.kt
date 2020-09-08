package enxy.githubapitest.ui.main.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import enxy.githubapitest.R
import enxy.githubapitest.data.network.entity.Repository
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter : ListAdapter<Repository, RepositoriesAdapter.RepositoryHolder>(RepositoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_repository, parent, false
        )
        return RepositoryHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(githubRepository: Repository) = with(itemView) {
            repository.text = githubRepository.name
            author.text = githubRepository.author
            Glide.with(itemView)
                .load(githubRepository.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(avatar)
        }
    }
}

class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}
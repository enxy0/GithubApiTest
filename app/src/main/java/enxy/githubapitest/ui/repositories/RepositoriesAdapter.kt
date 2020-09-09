package enxy.githubapitest.ui.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import enxy.githubapitest.R
import enxy.githubapitest.data.network.entity.Repository
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter(private val onLoadMore: (lastRepoId: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: ArrayList<Repository?> = ArrayList()
    private var isLoading = false
    private var isErrorHintShown = false

    companion object {
        const val VIEW_TYPE_ITEM = 0 // shows repository
        const val VIEW_TYPE_LOADING = 1 // shows loading (try again)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && lastVisibleItem == itemCount - 1) {
                    data[lastVisibleItem - 1]?.id?.let { onLoadMore(it) }
                    isLoading = true
                }
            }
        })
    }


    fun addAll(repositories: List<Repository>) {
        data.addAll(repositories)
        notifyDataSetChanged()
        addLoadingState()
    }

    fun addMore(repositories: List<Repository>) {
        val lastIndex = data.size - 1
        data.removeLast() // removing loading state
        data.addAll(repositories)
        isLoading = false
        notifyItemRemoved(lastIndex)
        notifyItemRangeInserted(lastIndex, repositories.size - 1)
        addLoadingState()
    }

    private fun addLoadingState() {
        data.add(null)
        notifyItemInserted(itemCount - 1)
    }

    fun showTryAgainHint() {
        isErrorHintShown = true
        notifyItemChanged(itemCount - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_repository, parent, false
            )
            RepositoryHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_loading, parent, false
            )
            LoadingHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryHolder) {
            data[position]?.let { holder.bind(it) }
        } else {
            val loadingHolder = (holder as LoadingHolder)
            if (isErrorHintShown) {
                loadingHolder.showTryAgainHint()
            } else {
                loadingHolder.startLoading()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position] != null) VIEW_TYPE_ITEM else VIEW_TYPE_LOADING
    }

    override fun getItemId(position: Int): Long {
        return data[position]?.id?.toLong() ?: 0
    }

    class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(githubRepository: Repository) = with(itemView) {
            repository.text = githubRepository.name
            login.text = githubRepository.owner.login
            Glide.with(itemView)
                .load(githubRepository.owner.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(avatar)
        }
    }

    inner class LoadingHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun startLoading() = with(itemView) {
            progressBar.isVisible = true
            tryAgainHint.isInvisible = true
        }

        fun showTryAgainHint() = with(itemView) {
            tryAgainHint.isVisible = true
            progressBar.isInvisible = true
            tryAgainButton.setOnClickListener {
                startLoading()
                onLoadMore(itemCount - 1)
                isErrorHintShown = false
                itemView.tryAgainHint.isInvisible = true
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
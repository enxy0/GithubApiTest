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
import enxy.githubapitest.data.network.entity.repository.Repository
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter(private val repositoryCallback: RepositoryCallback) :
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
                    data[lastVisibleItem - 1]?.id?.let { repositoryCallback.onLoadMore(it) }
                    isLoading = true
                }
            }
        })
    }

    fun addAll(repositories: List<Repository>) {
        val lastIndex = data.size - 1
        data.clear()
        data.addAll(repositories)
        isLoading = false
        notifyItemRemoved(lastIndex) // telling recycler view about deleted loading state (null)
        notifyItemRangeInserted(lastIndex, repositories.size - 1) // notifying about
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

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(githubRepository: Repository) = with(itemView) {
            repositoryCard.setOnClickListener {
                repositoryCallback.onDetailsClicked(githubRepository)
            }
            repository.text = githubRepository.name
            repositoryAuthorLogin.text = githubRepository.owner.login
            Glide.with(itemView)
                .load(githubRepository.owner.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(repositoryAuthorAvatar)
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
                data[itemCount - 1]?.let { lastRepo ->
                    repositoryCallback.onLoadMore(lastRepo.id)
                }
                isErrorHintShown = false
                itemView.tryAgainHint.isInvisible = true
            }
        }
    }
}
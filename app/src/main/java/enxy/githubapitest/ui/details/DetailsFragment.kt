package enxy.githubapitest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.bumptech.glide.Glide
import enxy.githubapitest.R
import enxy.githubapitest.data.network.entity.repository.Repository
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.details_fragment.view.*
import kotlinx.android.synthetic.main.item_loading.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModel()
    private val parentAdapter: ParentAdapter by inject()
    val repository: Repository by lazy {
        requireArguments().getParcelable(REPOSITORY_KEY)!!
    }

    companion object {
        private const val REPOSITORY_KEY = "repo"

        fun newInstance(repository: Repository): DetailsFragment {
            val args = Bundle().apply {
                putParcelable(REPOSITORY_KEY, repository)
            }
            val fragment = DetailsFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getCommitDetails(repository.commitsUrl)

        val view = inflater.inflate(R.layout.details_fragment, container, false)
        showLoading(view)
        view.repository.text = getString(R.string.title_repository, repository.name)
        view.repositoryAuthorLogin.text = repository.owner.login
        Glide.with(view)
            .load(repository.owner.avatarUrl)
            .circleCrop()
            .into(view.repositoryAuthorAvatar)

        with(view.commitParentsList) {
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            adapter = parentAdapter
        }

        viewModel.commitDetails.observe(viewLifecycleOwner) {
            it.onSuccess { details ->
                commitMessage.text = details.commit.message
                commitAuthorLogin.text = details.commit.author.name
                commitDate.text = details.commit.author.date
                parentAdapter.addAll(details.parentsSha)
                hideHintLayout(view)
            }.onFailure {
                hideLoading(view)
                showTryAgainHint(view)
            }
        }
        return view
    }


    private fun showLoading(view: View) {
        view.lastCommitSection.isGone = true
        view.progressBar.isVisible = true
    }

    private fun hideLoading(view: View) {
        view.progressBar.isGone = true
        view.lastCommitSection.isVisible = true
    }

    private fun showTryAgainHint(view: View) {
        view.lastCommitSection.isGone = true
        view.tryAgainHint.isVisible = true
        view.tryAgainButton.setOnClickListener {
            viewModel.getCommitDetails(repository.commitsUrl)
            showLoading(view)
            view.tryAgainHint.isInvisible = true
        }
    }

    private fun hideHintLayout(view: View) {
        view.hintLayout.isGone = true
        view.lastCommitSection.isVisible = true
    }


}
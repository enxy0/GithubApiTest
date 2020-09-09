package enxy.githubapitest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import enxy.githubapitest.R
import enxy.githubapitest.data.network.entity.Repository
import kotlinx.android.synthetic.main.details_fragment.view.*
import org.koin.android.ext.android.inject

class DetailsFragment : Fragment() {
    val viewModel: DetailsViewModel by inject()

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
        val repository: Repository = arguments?.getParcelable(REPOSITORY_KEY)
            ?: throw Exception("Unsupported Behavior")
        val view = inflater.inflate(R.layout.details_fragment, container, false)
        view.repository.text = repository.name
        view.login.text = repository.owner.login
        Glide.with(view)
            .load(repository.owner.avatarUrl)
            .circleCrop()
            .into(view.avatar)
        return view
    }
}
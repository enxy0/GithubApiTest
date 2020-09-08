package enxy.githubapitest.ui.main.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import enxy.githubapitest.R
import enxy.githubapitest.ui.main.RepositoryViewModel
import kotlinx.android.synthetic.main.main_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RepositoriesFragment : Fragment() {
    private val repositoriesAdapter by inject<RepositoriesAdapter>() {
        parametersOf(viewModel::onLoadMoreRepos)
    }
    private val viewModel: RepositoryViewModel by inject()

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        with(view.repositoryList) {
            val linearLayoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            layoutManager = linearLayoutManager
            adapter = repositoriesAdapter
            setHasFixedSize(true)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.repositories.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { repos ->
                    if (repositoriesAdapter.itemCount == 0) {
                        repositoriesAdapter.addAll(repos)
                    } else {
                        repositoriesAdapter.addMore(repos)
                    }
                },
                onFailure = { showNoInternetError() }
            )
        }
    }

    private fun showNoInternetError() {
        Toast.makeText(requireContext(), "No internet", Toast.LENGTH_SHORT).show()
    }
}
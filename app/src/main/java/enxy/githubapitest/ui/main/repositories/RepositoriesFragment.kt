package enxy.githubapitest.ui.main.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import enxy.githubapitest.R
import enxy.githubapitest.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class RepositoriesFragment : Fragment() {

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        with(repositoryList) {
            adapter = RepositoriesAdapter().apply {
                setHasStableIds(true)
                submitList(viewModel.getTestData())
            }
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            setHasFixedSize(true)
        }
    }
}
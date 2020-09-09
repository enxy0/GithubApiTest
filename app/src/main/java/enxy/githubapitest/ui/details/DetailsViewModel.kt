package enxy.githubapitest.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import enxy.githubapitest.data.network.GithubDataSource
import enxy.githubapitest.data.network.entity.details.Details
import enxy.githubapitest.utils.Result
import kotlinx.coroutines.launch

class DetailsViewModel(private val githubDataSource: GithubDataSource) : ViewModel() {
    private val _commitDetails: MutableLiveData<Result<Details>> = MutableLiveData()
    val commitDetails: LiveData<Result<Details>> = _commitDetails

    fun getCommitDetails(commitUrl: String) {
        viewModelScope.launch {
            _commitDetails.value = githubDataSource.getCommitDetails(commitUrl)
        }
    }
}
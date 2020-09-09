package enxy.githubapitest.ui.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import enxy.githubapitest.data.network.GithubDataSource
import enxy.githubapitest.data.network.entity.repository.Repository
import enxy.githubapitest.utils.Result
import enxy.githubapitest.utils.Result.Error
import enxy.githubapitest.utils.Result.Success
import kotlinx.coroutines.launch

class RepositoryViewModel(private val githubDataSource: GithubDataSource) : ViewModel() {
    private val repositories: ArrayList<Repository> = ArrayList() // local cache of repos

    private val _uiState: MutableLiveData<Result<List<Repository>>> = MutableLiveData()
    val uiState: LiveData<Result<List<Repository>>> = _uiState

    companion object {
        const val START_REPOSITORY_ID = 0
    }

    init {
        onLoadMoreRepos()
    }

    fun onLoadMoreRepos(lastRepoId: Int = START_REPOSITORY_ID) {
        viewModelScope.launch {
            when (val result = githubDataSource.getPublicRepositories(lastRepoId)) {
                is Success -> {
                    repositories.addAll(result.data)
                    _uiState.value = Success(repositories)
                }
                is Error -> {
                    _uiState.value = result
                }
            }
        }
    }
}
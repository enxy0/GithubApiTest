package enxy.githubapitest.ui.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import enxy.githubapitest.data.network.GithubApi
import enxy.githubapitest.data.network.entity.Owner
import enxy.githubapitest.data.network.entity.Repository
import enxy.githubapitest.utils.Result
import enxy.githubapitest.utils.Result.Error
import enxy.githubapitest.utils.Result.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryViewModel(private val githubApi: GithubApi) : ViewModel() {
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
            when (val result = getPublicRepositories(lastRepoId)) {
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

    private suspend fun getPublicRepositories(lastRepoId: Int): Result<ArrayList<Repository>> =
        withContext(Dispatchers.IO) {
            try {
                Success(githubApi.getPublicRepositories(lastRepoId))
            } catch (exception: Exception) {
                Error(exception)
            }
        }

    fun getTestData(): List<Repository> {
        return listOf(
            Repository(
                id = 365,
                name = "GithubApiTest",
                owner = Owner("enxy0", "https://avatars2.githubusercontent.com/u/128?v=4"),
                commitsUrl = "https://api.github.com/repos/defunkt/exception_logger/commits"
            ),
            Repository(
                id = 364,
                name = "acts_as_money",
                owner = Owner("collectiveidea", "https://avatars2.githubusercontent.com/u/128?v=4"),
                commitsUrl = "https://api.github.com/repos/defunkt/exception_logger/commits"
            )
        )
    }
}
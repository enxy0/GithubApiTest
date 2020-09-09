package enxy.githubapitest.data.network

import enxy.githubapitest.data.network.entity.Repository
import enxy.githubapitest.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubDataSource(private val githubApi: GithubApi) {
    suspend fun getPublicRepositories(lastRepoId: Int): Result<ArrayList<Repository>> =
        withContext(Dispatchers.IO) {
            try {
                Result.Success(githubApi.getPublicRepositories(lastRepoId))
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }
}

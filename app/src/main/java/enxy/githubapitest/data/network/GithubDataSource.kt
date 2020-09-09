package enxy.githubapitest.data.network

import enxy.githubapitest.data.network.entity.Repository
import enxy.githubapitest.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubDataSource(private val githubApi: GithubApi) {
    suspend fun getPublicRepositories(lastRepoId: Int): Result<List<Repository>> =
        withContext(Dispatchers.IO) {
            try {
                val data = githubApi.getPublicRepositories(lastRepoId)
                data.forEach {
                    it.commitsUrl = it.commitsUrl.dropLast(6) // removing {\sha}
                }
                Result.Success(data)
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }
}

package enxy.githubapitest.data.network

import com.google.gson.internal.bind.util.ISO8601Utils
import enxy.githubapitest.data.network.entity.details.Details
import enxy.githubapitest.data.network.entity.repository.Repository
import enxy.githubapitest.utils.Result
import enxy.githubapitest.utils.Result.Error
import enxy.githubapitest.utils.Result.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class GithubDataSource(private val githubApi: GithubApi) {
    suspend fun getPublicRepositories(lastRepoId: Int): Result<List<Repository>> {
        return withContext(Dispatchers.Default) {
            try {
                val data = githubApi.getPublicRepositories(lastRepoId)
                data.forEach {
                    it.commitsUrl = it.commitsUrl.dropLast(6) // removing {\sha}
                }
                Success(data)
            } catch (exception: Exception) {
                Error(exception)
            }
        }
    }

    suspend fun getCommitDetails(commitUrl: String): Result<Details> {
        return withContext(Dispatchers.Default) {
            try {
                val data = githubApi.getCommitDetails(commitUrl)
                val lastCommit = data.first().apply {
                    commit.author.date = commit.author.date formatTo "dd.MM.YYYY"
                }
                Success(lastCommit)
            } catch (exception: Exception) {
                Error(exception)
            }
        }
    }
}

private infix fun String.formatTo(pattern: String): String {
    val githubDate = ISO8601Utils.parse(this, ParsePosition(0))
    return SimpleDateFormat(pattern, Locale.getDefault()).format(githubDate)
}

package enxy.githubapitest.data.network

import enxy.githubapitest.data.network.entity.details.Details
import enxy.githubapitest.data.network.entity.repository.Repository
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubApi {
    @GET("/repositories")
    suspend fun getPublicRepositories(@Query("since") lastRepoId: Int): List<Repository>

    @GET
    suspend fun getCommitDetails(@Url url: String): ArrayList<Details>
}
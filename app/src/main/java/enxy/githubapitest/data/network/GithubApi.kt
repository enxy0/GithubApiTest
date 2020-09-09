package enxy.githubapitest.data.network

import enxy.githubapitest.data.network.entity.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/repositories")
    suspend fun getPublicRepositories(@Query("since") lastRepoId: Int): ArrayList<Repository>
}
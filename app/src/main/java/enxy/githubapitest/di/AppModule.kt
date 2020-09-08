package enxy.githubapitest.di

import enxy.githubapitest.data.network.GithubApi
import enxy.githubapitest.ui.main.RepositoryViewModel
import enxy.githubapitest.ui.main.repositories.RepositoriesAdapter
import enxy.githubapitest.utils.GITHUB_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
    factory { (onLoadMore: (lastRepoId: Int) -> Unit) ->
        RepositoriesAdapter(onLoadMore).apply {
            setHasStableIds(true)
        }
    }
    viewModel {
        RepositoryViewModel(get())
    }
}
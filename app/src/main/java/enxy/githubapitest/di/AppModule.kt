package enxy.githubapitest.di

import enxy.githubapitest.data.network.GithubApi
import enxy.githubapitest.data.network.GithubDataSource
import enxy.githubapitest.ui.details.DetailsViewModel
import enxy.githubapitest.ui.repositories.RepositoriesAdapter
import enxy.githubapitest.ui.repositories.RepositoryCallback
import enxy.githubapitest.ui.repositories.RepositoryViewModel
import enxy.githubapitest.utils.GITHUB_URL
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
    single {
        OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()
    }

    single {
        GithubDataSource(get())
    }

    factory { (repositoryCallback: RepositoryCallback) ->
        RepositoriesAdapter(repositoryCallback).apply {
            setHasStableIds(true)
        }
    }
    viewModel {
        RepositoryViewModel(get())
    }

    viewModel {
        DetailsViewModel()
    }
}
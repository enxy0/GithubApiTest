package enxy.githubapitest.ui.repositories

import enxy.githubapitest.data.network.entity.repository.Repository

interface RepositoryCallback {
    fun onDetailsClicked(repository: Repository)

    fun onLoadMore(lastRepoId: Int)
}
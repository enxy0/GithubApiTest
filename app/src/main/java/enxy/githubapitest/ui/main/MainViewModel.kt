package enxy.githubapitest.ui.main

import androidx.lifecycle.ViewModel
import enxy.githubapitest.data.network.entity.Commit
import enxy.githubapitest.data.network.entity.Repository

class MainViewModel : ViewModel() {
    fun getTestData(): List<Repository> {
        return listOf(
            Repository(
                id = 365,
                name = "acts_as_money",
                author = "collectiveidea",
                avatarUrl = "https://avatars2.githubusercontent.com/u/128?v=4",
                lastCommit = Commit(
                    message = "Remove monkey patches to the Money gem. See http://github.com/collectiveidea/money",
                    author = "Brandon Keepers",
                    date = "2008-11-19T21:33:21Z",
                    parentsSha = listOf(
                        "1f00ea116c21048f57bf7a5c8c4c661c011ffaf3"
                    )
                )
            ),
            Repository(
                id = 365,
                name = "acts_as_money",
                author = "collectiveidea",
                avatarUrl = "https://avatars2.githubusercontent.com/u/128?v=4",
                lastCommit = Commit(
                    message = "Remove monkey patches to the Money gem. See http://github.com/collectiveidea/money",
                    author = "Brandon Keepers",
                    date = "2008-11-19T21:33:21Z",
                    parentsSha = listOf(
                        "1f00ea116c21048f57bf7a5c8c4c661c011ffaf3"
                    )
                )
            )
        )
    }
}
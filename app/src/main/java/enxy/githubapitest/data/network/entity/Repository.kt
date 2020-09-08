package enxy.githubapitest.data.network.entity

data class Repository(
    val id: Long,
    val name: String,
    val author: String,
    val avatarUrl: String,
    val lastCommit: Commit
)

data class Commit(
    val message: String,
    val author: String,
    val date: String,
    val parentsSha: List<String>
)
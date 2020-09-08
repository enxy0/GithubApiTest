package enxy.githubapitest.data.network.entity

data class Commit(
    val message: String,
    val owner: String,
    val date: String,
    val parentsSha: List<String>
)
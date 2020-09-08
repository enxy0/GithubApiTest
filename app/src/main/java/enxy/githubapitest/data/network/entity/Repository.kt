package enxy.githubapitest.data.network.entity

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("commits_url") val commitsUrl: String
)
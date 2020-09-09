package enxy.githubapitest.data.network.entity.details

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("name") val name: String,
    @SerializedName("date") var date: String
)
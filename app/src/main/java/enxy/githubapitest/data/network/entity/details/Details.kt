package enxy.githubapitest.data.network.entity.details

import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("commit") val commit: Commit,
    @SerializedName("parents") val parentsSha: List<Parent>
)
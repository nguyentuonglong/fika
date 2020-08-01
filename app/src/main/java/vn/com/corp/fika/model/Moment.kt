package vn.com.corp.fika.model


import com.google.gson.annotations.SerializedName

data class Moment(
    @SerializedName("date")
    val date: Int? = 0,
    @SerializedName("images")
    val images: List<String?>? = listOf(),
    @SerializedName("latlong")
    val latlong: String? = "",
    @SerializedName("location")
    val location: String? = ""
)
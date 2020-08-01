package vn.com.corp.fika.model


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("bio")
    val bio: String? = "",
    @SerializedName("birthdate")
    val birthdate: Int? = 0,
    @SerializedName("education")
    val education: String? = "",
    @SerializedName("hobbies")
    val hobbies: List<String?>? = listOf(),
    @SerializedName("images")
    val images: List<String?>? = listOf(),
    @SerializedName("moment")
    val moment: List<Moment?>? = listOf(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("songs")
    val songs: List<String?>? = listOf(),
    @SerializedName("username")
    val username: String? = "",
    @SerializedName("work")
    val work: String? = "",
    @SerializedName("work_date")
    val workDate: Int? = 0
)
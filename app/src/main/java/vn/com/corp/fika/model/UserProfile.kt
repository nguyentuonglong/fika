package vn.com.corp.fika.model


import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("profile")
    val profile: Profile? = Profile()
)
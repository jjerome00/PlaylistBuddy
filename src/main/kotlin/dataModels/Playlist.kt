package dataModels

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("song_ids")
    val songIds: List<Int>
)
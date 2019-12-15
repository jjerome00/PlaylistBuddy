package dataModels

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: Int,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("song_ids")
    var songIds: MutableList<Int>
)
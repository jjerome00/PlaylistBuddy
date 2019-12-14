package service

import com.google.gson.*
import dataModels.Mixtape
import dataModels.Playlist
import dataModels.Song
import dataModels.User
import java.lang.reflect.Type

class MixtapeSerializer : JsonSerializer<Mixtape> {
    
    override fun serialize(mixtape: Mixtape?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val json = JsonObject()
        if (mixtape != null) {
            mixtape.users?.let {
                json.add("users", serializeUsers(it))
            }
            mixtape.playlists?.let {
                json.add("playlists", serializePlaylists(it))
            }
            mixtape.songs?.let {
                json.add("songs", serializeSongs(it))
            }
        }
        return json
    }

    private fun serializeUsers(users: List<User>): JsonArray {
        val jsonArray = JsonArray()
        users.forEach { user ->
            val jsonRow = JsonObject()
            jsonRow.add("id", JsonPrimitive(user.id))
            jsonRow.add("name", JsonPrimitive(user.name))
            jsonArray.add(jsonRow)
        }
        return jsonArray
    }

    private fun serializePlaylists(playlists: List<Playlist>): JsonArray {
        val jsonArray = JsonArray()
        playlists.forEach { playlist ->
            val jsonRow = JsonObject()
            jsonRow.add("id", JsonPrimitive(playlist.id))
            jsonRow.add("user_id", JsonPrimitive(playlist.userId))
            jsonRow.add("song_ids", serializePlaylistSongList(playlist.songIds))
            jsonArray.add(jsonRow)
        }
        return jsonArray
    }


    private fun serializePlaylistSongList(playlistSongs: List<Int>): JsonArray {
        val jsonArray = JsonArray()
        playlistSongs.forEach { song ->
            jsonArray.add(JsonPrimitive(song))
        }
        return jsonArray
    }

    private fun serializeSongs(songs: List<Song>): JsonArray {
        val jsonArray = JsonArray()
        songs.forEach { song ->
            val jsonRow = JsonObject()
            jsonRow.add("id", JsonPrimitive(song.id))
            jsonRow.add("artist", JsonPrimitive(song.artist))
            jsonRow.add("title", JsonPrimitive(song.title))
            jsonArray.add(jsonRow)
        }
        return jsonArray
    }
}
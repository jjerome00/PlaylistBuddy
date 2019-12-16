package dataModels

import changeModels.PlaylistAddSongs
import changeModels.PlaylistNew


data class ProcessResult(
    val success: Boolean,
    val message: String

)

data class Mixtape(
    var users: MutableList<User>?,
    var playlists: MutableList<Playlist>?,
    var songs: MutableList<Song>?
) {
    fun userExists(userId : Int) : Boolean {
        val user = this.users?.find { it.id == userId }
        return user != null
    }

    fun songExists(songId: Int) : Boolean {
        val song = this.songs?.find { it.id == songId }
        return song != null
    }

    fun allSongsExist(songs: List<Int>) : Boolean {
        songs.forEach { song ->
            if (!songExists(song)) {
                return false
            }
        }
        return true
    }

    fun playlistExists(playlistId: Int) : Boolean {
        val playlist = this.playlists?.find { it.id == playlistId }
        return playlist != null
    }

    /**
     * Build a result message in the form of:
     * successHeader: list of successful changes
     * failHeader: list of failed changes
     */
    fun buildResultMessage(successList: String, failList: String, successHeader: String, failHeader: String) : String {
        var finalMessage = ""
        var successMessage = ""
        var failMessage = ""

        if (successList.isNotEmpty()) {
            successMessage = "$successHeader: $successList"
            finalMessage = successMessage
        }

        if (failList.isNotEmpty()) {
            failMessage = "$failHeader: $failList"

            finalMessage += if (finalMessage.isNotEmpty()) "\n$failMessage" else "$failMessage"
        }

        return finalMessage
    }

    fun deletePlaylists(deleteList: List<Int>) : String {
        var successList = ""
        var failList = ""

        if (this.playlists.isNullOrEmpty()) {
            return "No playlists exist to delete"
        }

        deleteList.forEach { id ->
            this.playlists?.let { list ->
                val index = list.indexOf(list.find { playlist -> playlist.id == id })
                if (index >= 0) {
                    list.removeAt(index)
                    successList += if (successList.isNotEmpty()) ", $id" else "$id"
                } else {
                    failList += if (failList.isNotEmpty()) ", $id" else "$id"
                }
            }
        }

        return buildResultMessage(successList, failList, "Playlists removed", "Playlists skipped")
    }

    fun addPlaylists(newPlaylists: List<PlaylistNew>) : String {
        var successList = ""
        var failList = ""

        newPlaylists.forEach { newItem ->
            var newId = 0

            // find a new Id for the playlist
            if (this.playlists != null) {
                this.playlists!!.sortBy { it.id }
                newId = this.playlists!!.last().id + 1
            } // newId = 0

            if ( this.userExists(newItem.user_id) && this.allSongsExist(newItem.songs) ) {
                val playlist = Playlist(newId, newItem.user_id, newItem.songs.toMutableList())

                if (this.playlists == null) {
                    this.playlists = mutableListOf(playlist)
                } else {
                    this.playlists!!.add(playlist)
                }
                successList += if (successList.isNotEmpty()) ", $newId" else "$newId"
            } else {
                failList += if (failList.isNotEmpty()) ", ${newItem.user_id}" else "${newItem.user_id}"
            }
        }
        return buildResultMessage(successList, failList, "Playlists added (IDs)", "Playlists not added for user(s)")
    }

    fun addSongsToPlaylists(playlistAddSongs: List<PlaylistAddSongs>) : String {
        var successList = ""
        var failList = ""

        if (this.playlists.isNullOrEmpty()) {
            return "No playlists exist to add a song to"
        }

        playlistAddSongs.forEach { row ->
            this.playlists?.let {playlists ->
                var atLeastOneAdded = false
                val playlist = playlists.find { it.id == row.id }

                row.songs.forEach { song ->
                    if (this.songExists(song)) {
                        playlist?.songIds?.add(song)
                        atLeastOneAdded = true
                    }
                }

                if (atLeastOneAdded) {
                    successList += if (successList.isNotEmpty()) ", ${row.id}" else "${row.id}"
                } else {
                    failList += if (failList.isNotEmpty()) ", ${row.id}" else "${row.id}"
                }
            }
        }

        return buildResultMessage(successList, failList, "Songs added to Playlist Id(s)", "Playlists skipped")
    }

}
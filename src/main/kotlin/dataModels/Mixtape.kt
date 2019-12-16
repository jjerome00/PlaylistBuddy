package dataModels

import changeModels.PlaylistAddSongs
import changeModels.PlaylistNew


data class Mixtape(
    var users: MutableList<User>?,
    var playlists: MutableList<Playlist>?,
    var songs: MutableList<Song>?
) {
    private fun userExists(userId : Int) : Boolean {
        val user = this.users?.find { it.id == userId }
        return user != null
    }

    private fun songExists(songId: Int) : Boolean {
        val song = this.songs?.find { it.id == songId }
        return song != null
    }

    private fun allSongsExist(songs: List<Int>) : Boolean {
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
    private fun buildResultMessage(successList: String, failList: String, successHeader: String, failHeader: String) : String {
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

    /**
     * Delete playlists given in the list
     * Returns a user-friendly result string
     */
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

    /**
     * Add playlists given in the list
     *
     * Note: If even one song is missing, the playlist will not be added
     * (I did this because I wanted to handle it differently than adding a song)
     *
     * Returns a user-friendly result string for display
     */
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

    /**
     * Add a list of songs to a given playlist
     * If a song does not exist, it will not be added to the playlist (the others will)
     * Returns a user-friendly result string for display
     */
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
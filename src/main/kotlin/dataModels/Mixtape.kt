package dataModels

import changeModels.PlaylistAddSongs
import changeModels.PlaylistNew

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

    fun deletePlaylists(deleteList: List<Int>) : String {
        deleteList.forEach { id ->
            this.playlists?.let { list ->
                val index = list.indexOf( list.find { playlist -> playlist.id == id } )
                if (index >= 0) {
                    list.removeAt(index)
                }
            }
        }
        return "OK"
    }

    fun addPlaylists(newPlaylists: List<PlaylistNew>) : String {
        newPlaylists.forEach { newItem ->
            var newId = 0

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
            }
        }
        return "OK"
    }

    fun addSongsToPlaylists(playlistAddSongs: List<PlaylistAddSongs>) : String {
        playlistAddSongs.forEach { row ->
            this.playlists?.let {playlists ->
                val playlist = playlists.find { it.id == row.id }
                row.songs.forEach { song ->
                    if (this.songExists(song)) {
                        playlist?.songIds?.add(song)
                    }
                }
            }
        }
        return "OK"
    }

}
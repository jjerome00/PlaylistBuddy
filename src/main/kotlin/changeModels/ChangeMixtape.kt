package changeModels

import dataModels.Mixtape
import dataModels.Playlist

data class ChangeMixtape (
    val playlist_add_songs: List<PlaylistAddSongs>?,
    val playlist_new: List<PlaylistNew>?,
    val playlist_delete: List<Int>?
)
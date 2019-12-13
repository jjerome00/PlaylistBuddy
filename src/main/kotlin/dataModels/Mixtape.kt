package dataModels

data class Mixtape(
    val users: List<User>,
    val playlists: List<Playlist>,
    val songs: List<Song>
)
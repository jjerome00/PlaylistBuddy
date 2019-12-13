package dataModels

import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistModelTest {

    @Test
    fun parse_User() {
        val json = """
            {
              "id" : "1",
              "user_id" : "2",
              "song_ids" : [
                "8",
                "32"
              ]
            }
        """.trimIndent()

        val playlist = Gson().fromJson(json, Playlist::class.java)
        assertEquals(1, playlist.id)
        assertEquals(2, playlist.userId)
        assertEquals(2, playlist.songIds.size)
    }

}
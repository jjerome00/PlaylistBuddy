package changeModels

import com.google.gson.Gson
import dataModels.Mixtape
import dataModels.Playlist
import junit.framework.Assert.assertFalse
import junit.framework.TestCase.*
import org.junit.Test

class ChangeMixtapeTest {

    val mixtapeJson = """
        {
          "users" : [
            {
              "id": "2",
              "name": "Bob Denver"
            },
            {
              "id": "3",
              "name": "Margaret Hamilton"
            }
          ],
          "playlists" : [
            {
              "id": "2",
              "user_id": "2",
              "song_ids": ["1"]
            }
          ],
          "songs": [
            {
              "id": "1",
              "artist": "The Magnetic Fields",
              "title": "The Book of Love"
            },
            {
              "id": "2",
              "artist": "Arcade Fire",
              "title": "Everything Now"
            }
          ]
        }
    """.trimIndent()


    @Test
    fun parse_changeFile() {
        // GIVEN a json of a mixtape
        val json = ClassLoader.getSystemResource("sample_changes.json").readText()
        // WHEN I parse the json file
        val changes = Gson().fromJson(json, ChangeMixtape::class.java)
    }

    @Test
    fun playlist_deletes_item() {
        // GIVEN a Mixtape
        val mixtape = Gson().fromJson(mixtapeJson, Mixtape::class.java)

        // confirm what we will be changing
        assertTrue(mixtape.playlistExists(2))
        assertFalse(mixtape.playlistExists(3))

        // GIVEN a Changes object with delete playlist instructions
        val changesJson = """ { "playlist_delete" : [2, 3] } """.trimIndent()
        val changes = Gson().fromJson(changesJson, ChangeMixtape::class.java)

        // WHEN I instruct the mixtape with the changes
        changes.playlist_delete?.let {
            val result = mixtape.deletePlaylists(it)
            println(result)
        }

        // THEN I get the desired results
        assertFalse(mixtape.playlistExists(2))

        // never existed, but process should ignore these
        assertFalse(mixtape.playlistExists(3))
    }

    @Test
    fun playlist_add_song() {
        // GIVEN a Mixtape
        val mixtape = Gson().fromJson(mixtapeJson, Mixtape::class.java)

        // GIVEN a Changes object with add songs playlist instructions
        // GIVEN a non-existing song listed as a song to add
        val changesJson = """
            {
              "playlist_add_songs" : [
                {
                  "id": 2,
                  "songs": [1, 8]
                }
              ]
            }
        """.trimIndent()

        val changes = Gson().fromJson(changesJson, ChangeMixtape::class.java)

        // WHEN I instruct the mixtape to make the changes
        changes.playlist_add_songs?.let {
            val result = mixtape.addSongsToPlaylists(it)
        }

        // THEN the playlist has 2 of the same songs (I'm allowing duplicate songs)
        val playlist = mixtape.playlists?.find { it.id == 2 }
        val songCount = playlist?.songIds?.count { it == 1 }
        assertEquals(2, songCount)

        // THEN a non-existing song has not been added to the playlist
        val songCountForNonExistingSong = playlist?.songIds?.count { it == 2 }
        assertEquals(0, songCountForNonExistingSong)
    }

    @Test
    fun add_playlist() {
        // GIVEN a Mixtape
        val mixtape = Gson().fromJson(mixtapeJson, Mixtape::class.java)

        // GIVEN a Changes object with playlists to add (1st)
        val changesJson = """
            {
              "playlist_new" : [
                {
                  "user_id": 3,
                  "songs": [1, 2]
                }
              ]
            }
        """.trimIndent()
        val changes = Gson().fromJson(changesJson, ChangeMixtape::class.java)

        // WHEN I instruct the mixtape to make the changes
        changes.playlist_new?.let {
            val result = mixtape.addPlaylists(it)
        }

        // THEN the playlist has been added
        val playlist = mixtape.playlists?.find { it.userId == 3 }
        assertNotNull(playlist)
    }

    @Test
    fun add_playlist_nonexisting_user() {
        // GIVEN a Mixtape
        val mixtape = Gson().fromJson(mixtapeJson, Mixtape::class.java)

        // GIVEN a Changes object with a playlists with a non-existing user
        val changesJson = """
            {
              "playlist_new" : [
                {
                  "user_id": 1,
                  "songs": [1, 2]
                }
              ]
            }
        """.trimIndent()
        val changes = Gson().fromJson(changesJson, ChangeMixtape::class.java)

        // WHEN I instruct the mixtape to make the changes
        changes.playlist_new?.let {
            val result = mixtape.addPlaylists(it)
        }

        // THEN the playlist has NOT been added
        val playlist = mixtape.playlists?.find { it.userId == 1 }
        assertNull(playlist)
    }

    @Test
    fun add_playlist_nonexisting_song() {
        // GIVEN a Mixtape
        val mixtape = Gson().fromJson(mixtapeJson, Mixtape::class.java)

        // GIVEN a Changes object with a playlists with a non-existing song
        val changesJson = """
            {
              "playlist_new" : [
                {
                  "user_id": 3,
                  "songs": [1, 8]
                }
              ]
            }
        """.trimIndent()
        val changes = Gson().fromJson(changesJson, ChangeMixtape::class.java)

        // WHEN I instruct the mixtape to make the changes
        changes.playlist_new?.let {
            val result = mixtape.addPlaylists(it)
        }

        // THEN the playlist has NOT been added
        val playlist = mixtape.playlists?.find { it.userId == 3 }
        assertNull(playlist)
    }


}
package dataModels

import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SongModelTest {

    @Test
    fun parse_User() {
        val json = """
            {
              "id" : "1",
              "artist": "Camila Cabello",
              "title": "Never Be the Same"
            }
        """.trimIndent()

        val song = Gson().fromJson(json, Song::class.java)
        assertEquals(1, song.id)
        assertEquals("Camila Cabello", song.artist)
        assertEquals("Never Be the Same", song.title)
    }

}
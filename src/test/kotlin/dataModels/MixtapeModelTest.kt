package dataModels

import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MixtapeModelTest {

    @Test
    fun parseMixtape() {
        // GIVEN a json of a mixtape
        val json = ClassLoader.getSystemResource("mixtape.json").readText()

        // WHEN I parse the json file
        val mixtape = Gson().fromJson(json, Mixtape::class.java)

        // THEN I get the expected results
        // (these results are based on the file in the resources folder)
        assertEquals("Dipika Crescentia", mixtape.users[1].name)
        assertEquals(3, mixtape.playlists[1].userId)
        assertEquals("Zedd", mixtape.songs[1].artist)
    }

}
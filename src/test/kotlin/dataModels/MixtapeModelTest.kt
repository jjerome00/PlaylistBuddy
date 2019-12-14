package dataModels

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import service.MixtapeSerializer

class MixtapeModelTest {

    @Test
    fun parseMixtape() {
        // GIVEN a json of a mixtape
        val json = ClassLoader.getSystemResource("mixtape.json").readText()

        // WHEN I parse the json file
        val mixtape = Gson().fromJson(json, Mixtape::class.java)

        // THEN I get the expected results
        // (these results are based on the file in the resources folder)
        assertNotNull(mixtape.users)
        assertNotNull(mixtape.playlists)
        assertNotNull(mixtape.songs)

        assertEquals("Dipika Crescentia", mixtape.users!![1].name)
        assertEquals(3, mixtape.playlists!![1].userId)
        assertEquals("Zedd", mixtape.songs!![1].artist)
    }

    @Test
    fun serializeMixtape() {
        // GIVEN a mixtape object
        val json = ClassLoader.getSystemResource("mixtape.json").readText()
        val mixtapeOne = Gson().fromJson(json, Mixtape::class.java)

        // WHEN I serialize the object
        // THEN I get the expected json representation
        val gson = GsonBuilder().registerTypeAdapter(Mixtape::class.java, MixtapeSerializer()).create()
        val serializedResult = gson.toJson(mixtapeOne)

        // WHEN I deserialize the json (again)
        val mixtapeTwo = Gson().fromJson(serializedResult, Mixtape::class.java)

        // THEN I get a new mixtape object that's the same as the original
        assertEquals(mixtapeOne, mixtapeTwo)
    }

}
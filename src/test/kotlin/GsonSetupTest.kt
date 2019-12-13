import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Test


/**
 * Generic tests to make sure the gson library works on command line
 */
class GsonSetupTest() {

    data class Person(
        val id: Int,
        val name: String
    )

    @Test
    fun test_parse() {
        val json  = """
            {
                id: 1,
                name: "Jason Jerome"
            }
        """.trimIndent()

        val result = Gson().fromJson(json, Person::class.java)

        assertEquals("Jason Jerome", result.name)
    }

}
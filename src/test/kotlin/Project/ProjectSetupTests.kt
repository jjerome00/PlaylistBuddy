package Project

import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Test


/**
 * Generic Project tests
 */
class ProjectSetupTests() {

    /**
     * Test a generic gson parse to ensure gson library works from command line
     */
    @Test
    fun test_GsonParse() {
        data class Person(
            val id: Int,
            val name: String
        )

        val json  = """
            {
                id: 1,
                name: "Jason Jerome"
            }
        """.trimIndent()

        val result = Gson().fromJson(json, Person::class.java)

        assertEquals("Jason Jerome", result.name)
    }

    /**
     * Test loading a resource file - used this method on Android but not sure about terminal project
     */
    @Test
    fun test_readResourceFile() {
        val text = ClassLoader.getSystemResource("mixtape.json").readText()
        // If class loader fails the test will fail
    }

}
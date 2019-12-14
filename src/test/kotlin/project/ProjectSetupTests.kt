package project

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dataModels.Mixtape
import junit.framework.TestCase.assertEquals
import org.junit.Test
import service.MixtapeSerializer

/**
 * Generic project tests
 */
class ProjectSetupTests {

    data class Person(
        val id: Int,
        val name: String
    )

    /**
     * Test a generic gson parse to ensure gson library works from command line
     */
    @Test
    fun test_GsonLibrary() {
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
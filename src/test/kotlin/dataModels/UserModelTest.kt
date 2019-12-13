package dataModels

import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UserModelTest {

    @Test
    fun parse_User() {
        val name = "Albin Jaye"
        val json = """
            {
              "id" : "1",
              "name" : "$name"
            }
        """.trimIndent()

        val user = Gson().fromJson(json, User::class.java)
        assertEquals(1, user.id)
        assertEquals(name, user.name)
    }

}
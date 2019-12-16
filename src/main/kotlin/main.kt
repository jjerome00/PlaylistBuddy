import changeModels.ChangeMixtape
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dataModels.Mixtape
import service.MixtapeSerializer
import java.io.BufferedReader
import java.io.File

/**
 * I kept this here for convenience
 */
data class FileReadResult(
    val success: Boolean = false,
    val result: String
)

/**
 * Main functionality is done here, so I can easily control the messages printed back to the user.
 */
fun main(args: Array<String>) {
    println("HI! Working!\n")

    if (args.size < 3) {
        println("Usage: \$ application-name <input-file>.json <changes-file>.json <output-file>.json")
        println("")
        println("Example: ./playlistBuddy mixtape.json changes.json output.json")
        println("")
        println("Have fun!")
        println("")
        return
    }

    println("Results:")

    // load file names
    val mixtapeFile = args[0]
    val changesFile = args[1]
    val outputFileName = args[2]

    // read files
    val mixtapeResult = readFile(mixtapeFile)
    val changeResult = readFile(changesFile)

    if (!mixtapeResult.success) {
        println("Could not read the input file.")
        println("Please check the file and try again.")
        return
    }

    if (!changeResult.success) {
        println("Could not read the change file.")
        println("Please check the file and try again.")
        return
    }

    val mixtape : Mixtape?
    val changes : ChangeMixtape?

    // Create models
    try {
        mixtape = Gson().fromJson(mixtapeResult.result, Mixtape::class.java)
    } catch (e: Exception) {
        println("Problem with parsing input file: ${e.localizedMessage}")
        return
    }

    try {
        changes = Gson().fromJson(changeResult.result, ChangeMixtape::class.java)
    } catch (e: Exception) {
        println("Problem with parsing change file: ${e.localizedMessage}")
        return
    }

    changes.playlist_delete?.let {
        val result = mixtape.deletePlaylists(it)
        println(result)
    }

    changes.playlist_add_songs?.let {
        val result = mixtape.addSongsToPlaylists(it)
        println(result)
    }

    changes.playlist_new?.let {
        val result = mixtape.addPlaylists(it)
        println(result)
    }

    // output result
    val gson = GsonBuilder().registerTypeAdapter(Mixtape::class.java, MixtapeSerializer()).create()
    val serializedResult = gson.toJson(mixtape)

    val outputFile = File(outputFileName)
    outputFile.writeText(serializedResult)
}




fun readFile(fileName: String) : FileReadResult {
    val file = File(fileName)
    if (file.exists()) {
        val bufferedReader: BufferedReader = File(fileName).bufferedReader()
        val result = bufferedReader.use { it.readText() }
        return FileReadResult(true, result)
    }
    return FileReadResult(false, "")
}



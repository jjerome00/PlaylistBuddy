package service

import changeModels.ChangeMixtape
import com.google.gson.Gson
import dataModels.Mixtape
import java.io.BufferedReader
import java.io.File




class FileProcessor {

    fun processFiles(mixtapeJson: String, changesJson: String) : Mixtape {
        val mixtape = Gson().fromJson(mixtapeJson, Mixtape::class.java)
        val changes = Gson().fromJson(changesJson, ChangeMixtape::class.java)

        changes.playlist_delete?.let {
            val result = mixtape.deletePlaylists(it)
        }

        changes.playlist_add_songs?.let {
            val result = mixtape.addSongsToPlaylists(it)
        }

        changes.playlist_new?.let {
            val result = mixtape.addPlaylists(it)
        }
        return mixtape
    }



}

# playlistBuddy
An interview demo project by Jason Jerome

## Setup Project

### IntelliJ

Setup is already done. These steps are for reference.
 1. Create intelliJ project: Gradle, with Java and Kotlin/JVM
 2. Create new file: main.kt
 3. Add new Configuration:
    - Application (template)
    - Name: Playlist Buddy
    - Main class: `MainKt`
    - Use classpath of module: `playlsitBuddy.main`
 4. You should be able to run the project now

### Compile as jar
Refer to this document: https://kotlinlang.org/docs/tutorials/command-line.html
Dependencies: kotlin compiler

1. From project root: `kotlinc . -include-runtime -d playlistBuddy.jar`


## Changes file format

* The changes file should be in validate json format
* To keep it simple, actions are contained to objects

Sample file
```json
{
  "playlist_add_songs" : [
    {
      "id": 1,
      "songs": [1]
    }
  ],
  "playlist_new" : [
    {
      "user_id": 2,
      "songs": [1,8]
    }
  ],
  "playlist_delete" : [
    2, 3
  ]
}
```

**add songs to playlist**
construct an object with:
1. The id of the playlist
2. A list of songs

* Only songs that exist will be added
* If a song does not exist, it will be skipped

**add a new playlist**
construct an object with:
1. The user_id to assign the playlist
2. A list of songs

* User must exist, otherwise playlist will not be added
* If any song does not exist, the entire playlist will not be added

**delete a playlist**
construct an list of ids to remove

## Notes
* Error handling is a little sloppy, but I felt showing results looked better

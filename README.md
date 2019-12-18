
# playlistBuddy
A demo project for using Kotlin on the command line. 

The application accepts 3 arguments:
1. A `mixtape` file, which is a json representation of a mixtape with artists, playlists, and songs
2. A "changes" file, which is a json representation of changes you want to make to the `mixtape`
3. The name of output file with the results

## Usage

**From command line:**   
` java -jar <input-file>.json <changes-file>.json <output-file>.json `

Example:   
` java -jar playlistBuddy.jar mixtape.json changes.json output.json `

**From IntelliJ:**
1. Open project in IntelliJ
2. There should be a Application project already setup, named "Playlist Buddy"
3. Run

To change arguments for the files:
1. Edit configurations (menu: Run > Edit configurations...)
2. Select `Playlist Buddy` under Application
3. Change file names using `Program arguments` section

**Change file**
* See `Change File` section for format of changes file

## Setup

Setting up a Kotlin application program is a little more involved than setting it up for Android.
I included way too much stuff in git to ensure everything was there for when I submitted the application.

You can run the application directly in IntelliJ, or run the jar. How to build a new jar inside IntelliJ is explained below.

#### IntelliJ

Project setup is already done. These steps are for reference.
 1. Create intelliJ project: Gradle, with Java and Kotlin/JVM
 2. Create new file: main.kt
 3. Add new Configuration:
    - Application (template)
      - Name: Playlist Buddy
      - Main class: `MainKt`
      - Use classpath of module: `playlistBuddy.main`
      - use `Program arguments` to provide files (ex: `mixtape.json changes.json output.json`)
 4. You should be able to run the project now


#### Compile jar
1. File > Project Structure > Project Settings > Artifacts > Click green plus sign > Jar > From modules with dependencies...
2. Setup with these parameters:
  * Module: `<All Modules>`
  * Main Class: (select using folder chooser) `MainKt`
  * JAR files from libraries: `extract to the target JAR`
  * Directory for mainifest:
    * change from: `<project>\src\main\java`
    * to: `<project>\src\main\resources`
3. After the Artifact is displayed, check the box for `Include in project build`

**Build**
1. Choose: Build > Build Artifacts
2. Choose `Build` from menu
3. Jar will be built to: `<project root>/out/artifacts/playlistBuddy_jar/playlistBuddy.jar`
4. copy jar to project root: `cp ./out/artifacts/playlistBuddy_jar/playlistBuddy.jar .`

**Run**
From project root:   
`java -jar playlistBuddy.jar mixtape.json changes.json output.json`

## Change file

**Changes supported:**
1. Add an existing song to an existing playlist.
2. Add a new playlist for an existing user; the playlist should contain at least one existing song.
3. Remove an existing playlist.

**File format**
* The changes file should be a validate json file
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
* If a song does not exist, it will be skipped (others that do exist will be added)

**add a new playlist**   
construct an object with:
1. The user_id to assign the playlist
2. A list of songs

* User must exist, otherwise playlist will not be added
* If **any** song does not exist, the entire playlist will not be added

**delete a playlist**   
* construct a list of ids to remove


## Large file considerations
There are many modifications we can make to this application so it can handle very large files.

* The first thing I would do would be to create some tests. You'll never know all the way your application can break until you start testing it. You can either write tests yourself or use your user base to discover bugs.  
* The kotlin method I'm using (`File.readText()`) has an internal limitation of 2 GB. Anything larger than this would need a different method of reading files.
* We could use a caching/buffering mechanism for reading larger file sizes
* We would want to move the processing of the files to a different thread. This will allow the main application to be more responsive to the user.

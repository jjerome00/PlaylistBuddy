
# playlistBuddy
An interview demo project by Jason Jerome

## Setup

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


## Overview

In a programming language of your choice, create a console application that applies a batch of changes to an input file in order to create an output file.

In the instructions below, we provide you with the input JSON file, which is called mixtape.json, and we tell you the types of changes your application should support. You will design the structure of the changes file and you will write write the code that processes the specified changes and outputs a new file.

We'll expect to interact with your application like this:

$ application-name <input-file> <changes-file> <output-file>

For example:

$ killer-app mixtape.json changes.json output-file.json


Logistics
 * Keep it simple: focus on implementing the set of changes enumerated below. Your application doesn't need to handle any other operations.
 * Assume that other people have to run, read, and support your code, that we're going to run tests against it, and that future assignments will build upon it.
 * Use any language, tools, or solutions you see fit. Ideally, for a mobile position, we would like to see a mobile-appropriate language (Java, Kotlin, Obj-C, Swift, C#, etc.)

## Project Requirements

Here are the basic parameters for this exercise:

1. This input JSON file consists of a set of users, songs, and playlists that are part of a music service: mixtape.json.
2. Your application ingests mixtape.json.
3. Your application ingests a changes file, which can take whatever form you like (we use changes.json in our example, but you're free to make it text, YAML, CSV, or whatever). The changes file should include multiple changes in one file.
4. Your application outputs output.json in the same structure as mixtape.json, with the changes applied. The types of changes you need to support are ennumerated below and the application should process all changes in one pass.
5. Your solution includes a README that explains how to use your application.
6. Your README describes what changes you would need to make in order to scale this application to handle very large input files and/or very large changes files. Just describe the changes — you don't actually need to implement a scaled-up version of the application.
7. Don't worry about creating a UI, DB, server, or deployment.
8. Your code should be executable on Mac or Linux.

The types of changes your application needs to support are:

1. Add an existing song to an existing playlist.
2. Add a new playlist for an existing user; the playlist should contain at least one existing song.
3. Remove an existing playlist.

## Project Delivery

Send your working code and your README, which should also include instructions for how to run your project, including instructions about any dependencies.
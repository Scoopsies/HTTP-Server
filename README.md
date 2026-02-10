# HTTP Server from Scratch

A fully functional HTTP/1.1 server built in Java using only `java.net.ServerSocket` and `java.net.Socket` -- no external libraries or `java.net.http`.

## Features

### Core Server
- **Multithreaded** request handling -- each client connection is processed in its own thread
- **Configurable** via command-line arguments:
  - `-p <port>` -- set listening port (default: 80)
  - `-r <root>` -- set root directory for file serving (default: current directory)
  - `-h` -- display help
  - `-x` -- print configuration and exit

### HTTP Protocol Support
- Full HTTP/1.1 request parsing (method, path, headers, body)
- GET and POST method support
- Content-Length-based body reading
- Query string parameter parsing
- Cookie parsing and Set-Cookie response headers (with HttpOnly and Path attributes)
- Proper CRLF-delimited response formatting with status line, headers, and body

### Static File Serving
- Serves files from the configured root directory
- Automatic `index.html` detection for directories
- MIME type detection (HTML, plain text, JPEG, PNG, GIF, PDF, binary)
- Binary and text file support
- Generated HTML directory listings with clickable navigation
- 404 responses for missing resources

### Multipart Form Data
- Full `multipart/form-data` parsing with boundary detection
- Extracts filename, content type, and binary content from uploaded files
- Byte-level utilities for boundary splitting and slicing

### Route Handlers

| Route | Description |
|-------|-------------|
| `/hello` | Returns a simple HTML greeting |
| `/ping` | Displays start/end timestamps; optional `/ping/N` delays N seconds |
| `/form` | GET: displays query params; POST: shows uploaded file info (type, name, size) |
| `/listing` | Browsable directory listing with links to files and subdirectories |
| `/guess` | Cookie-powered number guessing game (1-100, max 7 guesses) |
| `/*` | Default handler -- serves static files or directory listings |

### Design
- **Strategy pattern** routing -- pluggable `RouteHandler` interface
- Socket abstraction layer (`ISocket` / `IServerSocket`) for testability
- `ResponseBuilder` for composing HTTP responses with status codes, headers, and body
- Comprehensive test suite using JUnit 5 with mock sockets and router spies

### Extensibility

The server is designed to be extended with new route handlers. Implement the `RouteHandler` interface, register your route with the `Router`, and you're up and running.

This architecture also makes the server easy to embed in other JVM languages. For example, [clojure-server](https://github.com/Scoopsies/clojure-server) builds on top of this project to add a fully playable tic-tac-toe game with AI opponents, cookie-based game state, and PostgreSQL persistence; all by implementing a single new route handler in Clojure.

## Building & Running

The easiest way to build and run the project is with [Maven](https://maven.apache.org/):

```
mvn compile
java -cp target/classes com.cleanCoders.Main
```

You can also package it as a JAR:

```
mvn package
java -cp target/HttpServer-1.0-SNAPSHOT.jar com.cleanCoders.Main
```

By default the server listens on port 80 and serves files from the current directory. Use flags to customize:

| Flag | Description | Default |
|------|-------------|---------|
| `-p <port>` | Set the listening port | `80` |
| `-r <root>` | Set the root directory for file serving | `.` (current directory) |
| `-h` | Print the help menu | |
| `-x` | Print the startup configuration and exit | |

A `testRoot` directory is included in the project with examples of many server features -- HTML pages, nested directories, directory listings, various MIME types (images, PDF, text), and 404 handling. To serve it on port 8080:

```
java -cp target/classes com.cleanCoders.Main -p 8080 -r ./testRoot
```

## Running Tests

```
mvn test
```

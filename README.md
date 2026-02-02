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

## Building & Running

```
mvn compile
mvn exec:java -Dexec.mainClass="Main" -Dexec.args="-p 8080 -r /path/to/serve"
```

## Running Tests

```
mvn test
```

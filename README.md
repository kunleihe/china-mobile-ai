# China Mobile AI Service (A Spring AI Demo)

A Spring Boot Demo integrating Spring AI to demonstrate RAG (Retrieval-Augmented Generation) and Function Calling capabilities with OpenAI services.

## Features

- **Stream Chat**: Server-Sent Events (SSE) based chat interface.
- **RAG Implementation**: Uses vector store embeddings for rule-based and city-specific data context.
- **Function Calling**: Integrated tools for querying weather and package data.
- **Vector Search**: Direct endpoint to search the vector database content.

## Tech Stack

- Java 17
- Spring Boot 3.3.5
- Spring AI 1.0.0-M6
- OpenAI API (GPT-3.5 Turbo, text-embedding-3-small)
- Lombok, Hutool

## Configuration

Set your OpenAI API key in `application.yml` or as an environment variable:

```bash
export OPENAI_API_KEY=your_api_key_here
```

## Running the Application

1. Build the project:
   ```bash
   mvn clean package
   ```
2. Run the JAR:
   ```bash
   java -jar target/china-mobile-ai-1.0-SNAPSHOT.jar
   ```

The application runs on port `8099`.

## API Endpoints

### Chat Stream
**POST** `/chat/stream`
```json
{
  "question": "What is the weather in Beijing?",
  "sessionId": "12345"
}
```

### Vector Search
**POST** `/chat/search?query=your_search_term`

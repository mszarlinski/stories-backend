# Source Code Analysis

**Stories** is a Medium clone built with Spring Boot implementing CQRS (Command Query Responsibility Segregation), event-driven architecture, and microservices patterns.

## Architecture Overview

The application follows a modular domain-driven design with clear separation between:

- **Publishing** (`src/main/java/.../publishing/`) - Write side (commands)
- **Reading** (`src/main/java/.../reading/`) - Read side (queries)
- **Authentication** (`src/main/java/.../auth/`) - User management
- **Shared Kernel** (`src/main/java/.../sharedkernel/`) - Common domain objects

## Technology Stack

- **Backend**: Spring Boot 2.4.0, Java 11
- **Database**: MongoDB with Spring Data
- **Security**: Spring Security with OAuth2 (Google authentication)
- **Testing**: JUnit 5, Testcontainers, Awaitility

## Top 5 Implemented Use Cases

### 1. **User Authentication & Authorization**
- **Location**: `SignInController.java:23`
- **Endpoint**: `POST /signin`
- **Implementation**: OAuth2 integration with Google Sign-In. Users authenticate via JWT tokens, and the system creates or retrieves user profiles with Google account details (name, email, picture).

### 2. **Publishing Stories**
- **Location**: `PublisherController.java:20`
- **Endpoint**: `POST /stories`
- **Implementation**: Authenticated users can publish new stories with title and content. Uses `StoryPublisherFacade.java:31` to create `PublishedStory` entities, save to MongoDB, and publish domain events.

### 3. **Viewing Homepage Stories**
- **Location**: `ReaderController.java:21`
- **Endpoint**: `GET /public/home/stories`
- **Implementation**: Public endpoint returning all stories for homepage display. Uses read model (`StoryReaderFacade.java:23`) to fetch denormalized story views with author information.

### 4. **Reading Individual Stories**
- **Location**: `ReaderController.java:30`
- **Endpoint**: `GET /public/stories/{storyId}`
- **Implementation**: Public endpoint to view specific stories by ID. Returns full story content including title, content, author, and publication date from the read model.

### 5. **Managing Published Stories**
- **Location**: `MyStoriesController.java:20`
- **Endpoint**: `GET /publisher/stories`
- **Implementation**: Authenticated endpoint for authors to view their own published stories. Returns list of stories with metadata (title, publication date) filtered by author ID.

The application demonstrates clean CQRS separation where write operations go through the publishing domain, while read operations use optimized read models updated via event handlers (`StoryPublishedEventHandler.java:19`).
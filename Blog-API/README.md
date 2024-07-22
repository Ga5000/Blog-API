# Blog API Documentation

## Authentication

The API uses JWT token authentication. Every request (except for login and registration endpoints) requires a valid JWT token in the Authorization header.

### Generating JWT Token

- **POST /auth/login**
    - Description: Authenticates a user and generates a JWT token.
    - Request Body: `AuthDTO`
      ```json
      {
        "username": "string",
        "password": "string"
      }
      ```
    - Response:
        - 200 OK: `LoginResponseDTO` with JWT token
        - 400 Bad Request: "Invalid credentials"

### Using JWT Token

Include the JWT token in the Authorization header for subsequent requests:


## AuthController

Handles user authentication and registration.

- **POST /auth/login**
    - Description: Authenticates a user and generates a JWT token.
    - Request Body: `AuthDTO`
      ```json
      {
        "username": "string",
        "password": "string"
      }
      ```
    - Response:
        - 200 OK: `LoginResponseDTO` with JWT token
        - 400 Bad Request: "Invalid credentials"

- **POST /auth/register**
    - Description: Registers a new user (Can only be used by admins).
    - Request Body: `RegisterDTO`
      ```json
      {
        "username": "string",
        "password": "string",
        "role": "string"
      }
      ```
    - Response:
        - 201 Created: "User created successfully"
        - 400 Bad Request: "User already exists" if the username is taken.

- **POST /auth/register/user**
    - Description: Registers a new user with default role.
    - Request Body: `RegisterUserDTO`
      ```json
      {
        "username": "string",
        "password": "string"
      }
      ```
    - Response:
        - 201 Created: "User created successfully"
        - 400 Bad Request: "User already exists" if the username is taken.

## CommentController

Manages comments related to posts and users.

- **POST /comment/create/{postId}/{userId}**
    - Description: Creates a new comment for a post.
    - Request Body: `CommentRequestDTO`
      ```json
      {
        "content": "string"
      }
      ```
    - Response:
        - 201 Created: "Comment created successfully"
        - 404 Not Found: "Post or user not found"

- **PUT /comment/update/{id}/{postId}/{userId}**
    - Description: Updates an existing comment.
    - Request Body: `CommentRequestDTO`
      ```json
      {
        "content": "string"
      }
      ```
    - Response:
        - 200 OK: "Comment updated successfully"
        - 404 Not Found: "Post or user or comment not found"

- **DELETE /comment/delete/{id}**
    - Description: Deletes a comment by ID.
    - Response:
        - 200 OK: "Comment deleted successfully"
        - 404 Not Found: "Comment not found"

## PostController

Manages posts and their details.

- **POST /posts/create**
    - Description: Creates a new post.
    - Request Body: Form data
        - title: string
        - image: file
        - content: string
    - Response:
        - 201 Created: "Post created successfully"

- **GET /posts/post-details/{id}**
    - Description: Retrieves details of a post by ID.
    - Response:
        - 200 OK: PostDetailsDTO
        - 404 Not Found: "Post not found"

- **GET /posts/summary**
    - Description: Retrieves summaries of all posts.
    - Response:
        - 200 OK: List of PostSummaryDTO

- **PUT /posts/update/{id}**
    - Description: Updates an existing post by ID.
    - Request Body: Form data
        - title: string
        - image: file
        - content: string
    - Response:
        - 200 OK: "Post updated successfully"
        - 404 Not Found: "Post not found"

- **DELETE /posts/delete/{id}**
    - Description: Deletes a post by ID.
    - Response:
        - 200 OK: "Post deleted successfully"
        - 404 Not Found: "Post not found"

## UserController

Manages user-related operations.

- **DELETE /users/delete/{id}**
    - Description: Deletes a user by ID (self-deletion).
    - Response:
        - 200 OK: "User deleted successfully"
        - 404 Not Found: "User not found"
        - 403 Forbidden: "User is not authenticated"

## Additional Notes

- **Security:** All endpoints (except for login and registration) require a valid JWT token in the Authorization header.
- **Testing:** Use tools like Postman or Swagger to test and validate your API endpoints.

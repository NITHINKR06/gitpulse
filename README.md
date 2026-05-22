# GitPulse 🔍

A Spring Boot REST API that aggregates GitHub profile data — repositories, stars, language breakdown, and top projects — and renders it as a clean visual profile card.

---

## Features

- Search any GitHub username from a home page
- REST API returning structured JSON profile data
- Language usage breakdown with percentages
- Top 5 repositories ranked by stars
- Total stars, forks, followers, and repo count
- Dark-themed Thymeleaf profile card UI
- Clean error handling for invalid usernames

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.5 |
| HTTP Client | Spring WebFlux WebClient |
| View | Thymeleaf |
| Build | Maven |
| API | GitHub REST API v3 |
| Auth | GitHub Personal Access Token |
| Env | spring-dotenv |

---

## Project Structure

```
src/main/java/com/gitpulse/gitpulse/
├── config/
│   └── WebClientConfig.java       # WebClient bean with GitHub auth headers
├── controller/
│   ├── ProfileController.java     # REST API endpoints
│   └── ViewController.java        # Thymeleaf HTML routes
├── service/
│   ├── GitHubService.java         # Orchestrates API calls and builds ProfileDTO
│   └── StatsService.java          # Processes stars, forks, language percentages
├── client/
│   └── GitHubClient.java          # WebClient wrapper for GitHub API calls
├── dto/
│   ├── GithubUserDTO.java         # Maps raw GitHub /users/{username} response
│   ├── RepoDTO.java               # Maps each repository object
│   └── ProfileDTO.java            # Final clean response shape
└── exception/
    └── UserNotFoundException.java # Custom 404 exception

src/main/resources/
├── application.properties
└── templates/
    ├── index.html                 # Search home page
    └── profile.html              # Visual profile card
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/profile/{username}` | Full profile JSON |
| GET | `/api/profile/{username}/stats` | Stars, forks, repos, followers |
| GET | `/api/profile/{username}/languages` | Language percentage map |
| GET | `/api/profile/{username}/repos` | Top 5 repos by stars |
| GET | `/profile/{username}` | HTML profile card (Thymeleaf) |
| GET | `/` | Search home page |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- GitHub Personal Access Token ([generate here](https://github.com/settings/tokens))

### Setup

**1. Clone the repo**
```bash
git clone https://github.com/NITHINKR06/gitpulse.git
cd gitpulse
```

**2. Create a `.env` file in the project root**
```
GITHUB_API_TOKEN=ghp_your_token_here
```

**3. Set `application.properties`**
```properties
spring.application.name=gitpulse
server.port=8080
github.api.base-url=https://api.github.com
github.api.token=${GITHUB_API_TOKEN}
```

**4. Run the app**
```bash
./mvnw spring-boot:run
```

**5. Open in browser**
```
http://localhost:8080
```

---

## Usage

1. Go to `http://localhost:8080`
2. Type any GitHub username and press Enter
3. View the profile card at `http://localhost:8080/profile/{username}`
4. Or hit the JSON API at `http://localhost:8080/api/profile/{username}`

### Example Response

```json
{
  "username": "NITHINKR06",
  "bio": "NMAMIT'27 | CyberSecurity | Full Stack developer",
  "totalRepos": 86,
  "followers": 27,
  "totalStars": 61,
  "totalForks": 4,
  "topRepos": [...],
  "languagePercentages": {
    "JavaScript": 35.2,
    "TypeScript": 31.0,
    "Python": 12.7,
    "Java": 8.5,
    "HTML": 9.9
  }
}
```

---

## Security Note

Never commit your `.env` or `application.properties` files. Both are listed in `.gitignore`. Your GitHub token should only have `public_repo` scope.

---

## Author

**Nithin K R**  
GitHub: [@NITHINKR06](https://github.com/NITHINKR06)

---

## License

MIT

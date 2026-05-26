<div align="center">

# GitPulse 🔍

**A Spring Boot REST API that transforms any GitHub username into a rich developer profile card with live stats, language analytics, async processing, and Redis caching.**

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Redis](https://img.shields.io/badge/Redis-Caching-red?style=flat-square&logo=redis)](https://redis.io)
[![GitHub API](https://img.shields.io/badge/GitHub-REST%20API%20v3-black?style=flat-square&logo=github)](https://docs.github.com/en/rest)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=flat-square&logo=docker)](https://www.docker.com)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

[Live Demo](#) · [API Docs](#api-endpoints) · [Report Bug](https://github.com/NITHINKR06/gitpulse/issues)

<p align="center">
  <img src="/public/gitplus-dashboard.jpeg" width="45%" />
  <img src="/public/profile.jpeg" width="45%" />
</p>

</div>

---

## What is GitPulse?

GitPulse hits the GitHub REST API, processes raw data through a stats engine, and returns a clean structured profile — available as both a **JSON API** and a **visual HTML card**. It's not a CRUD app. It's a tool developers actually use.

Built to demonstrate:
- Real-world API integration with rate limit handling via Redis cache
- Async processing with `@Async` + `CompletableFuture`
- Layered Spring Boot architecture (Controller → Service → Client → DTO)
- Docker + Docker Compose deployment

---

## Features

- 🔎 **Username search** — home page with instant routing to profile
- 📊 **Language analytics** — percentage breakdown across all public repos
- ⭐ **Stats aggregation** — total stars, forks, followers, original repo count
- 🏆 **Top repositories** — top 5 ranked by stars, excluding forks
- 🧠 **Most used language** — detected automatically from repo data
- ⚡ **Async processing** — repo fetch runs on a separate thread via `@Async`
- 🗄️ **Redis caching** — profiles cached for 10 minutes, prevents API rate limits
- 🎨 **Dark UI profile card** — Thymeleaf-rendered, GitHub-styled
- 🐳 **Docker ready** — single command to spin up app + Redis
- 🔒 **Secure by default** — token stored in `.env`, never committed

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| HTTP Client | Spring WebFlux WebClient |
| Async | `@Async` + `CompletableFuture` |
| Caching | Redis + Spring Cache (`@Cacheable`) |
| View Layer | Thymeleaf |
| Build Tool | Maven |
| External API | GitHub REST API v3 |
| Auth | GitHub Personal Access Token |
| Environment | spring-dotenv |
| Containerization | Docker + Docker Compose |

---

## Architecture

```
Client Request (git-username)
      │
      ▼
ProfileController          ← REST endpoints + exception handler
      │
      ▼
GitHubService              ← orchestrates calls, builds ProfileDTO
  ├── @Async fetchRepos()  ← runs on separate thread pool
  └── @Cacheable           ← Redis cache hit skips API call
      │
      ▼
GitHubClient               ← WebClient wrapper, calls GitHub API
      │
      ▼
StatsService               ← processes raw repos into analytics
      │
      ▼
ProfileDTO                 ← clean response shape returned to client
```

---

## Project Structure

```
src/main/java/com/gitpulse/gitpulse/
├── config/
│   ├── WebClientConfig.java       # WebClient bean with GitHub auth headers
│   └── RedisConfig.java           # Redis cache manager with TTL config
├── controller/
│   ├── ProfileController.java     # REST API endpoints + error handler
│   └── ViewController.java        # Thymeleaf HTML routes
├── service/
│   ├── GitHubService.java         # Async orchestration + cache layer
│   └── StatsService.java          # Stars, forks, language %, streak stats
├── client/
│   └── GitHubClient.java          # WebClient calls to GitHub REST API
├── dto/
│   ├── GithubUserDTO.java         # Maps raw GitHub /users/{username}
│   ├── RepoDTO.java               # Maps each repository object
│   └── ProfileDTO.java            # Final clean response shape
└── exception/
    └── UserNotFoundException.java # Custom 404 with clean error JSON

src/main/resources/
├── application.properties
└── templates/
    ├── index.html                 # Search home page
    └── profile.html               # Dark-themed profile card UI
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/` | Search home page |
| `GET` | `/profile/{username}` | HTML profile card |
| `GET` | `/api/profile/{username}` | Full profile JSON |
| `GET` | `/api/profile/{username}/stats` | Stars, forks, repos, followers |
| `GET` | `/api/profile/{username}/languages` | Language percentage map |
| `GET` | `/api/profile/{username}/repos` | Top 5 repos by stars |

### Example Response — `/api/profile/NITHINKR06`

```json
{
  "username": "NITHINKR06",
  "name": null,
  "bio": "NMAMIT'27 | CyberSecurity | Full Stack developer",
  "avatarUrl": "https://avatars.githubusercontent.com/u/146012853",
  "githubUrl": "https://github.com/NITHINKR06",
  "totalRepos": 86,
  "followers": 27,
  "totalStars": 61,
  "totalForks": 4,
  "originalRepos": 74,
  "mostUsedLanguage": "JavaScript",
  "topRepos": [
    {
      "name": "Hotel-room-booking-website",
      "language": "HTML",
      "stargazers_count": 2,
      "forks_count": 0
    }
  ],
  "languagePercentages": {
    "JavaScript": 35.2,
    "TypeScript": 31.0,
    "Python": 12.7,
    "Java": 8.5,
    "HTML": 9.9,
    "Jupyter Notebook": 2.8
  }
}
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker (for Redis)
- GitHub Personal Access Token → [Generate here](https://github.com/settings/tokens) with `public_repo` scope only

---

### Option A — Run with Docker Compose (recommended)

```bash
# 1. Clone
git clone https://github.com/NITHINKR06/gitpulse.git
cd gitpulse

# 2. Create .env file
echo "GITHUB_API_TOKEN=ghp_your_token_here" > .env

# 3. Run everything
docker-compose up --build
```

App runs at `http://localhost:8080` — Redis starts automatically.

---

### Option B — Run locally

```bash
# 1. Clone
git clone https://github.com/NITHINKR06/gitpulse.git
cd gitpulse

# 2. Create .env at project root
GITHUB_API_TOKEN=ghp_your_token_here

# 3. Start Redis (Docker)
docker run -d -p 6379:6379 redis

# 4. Run the app
./mvnw spring-boot:run

# 5. Open
http://localhost:8080
```

---

### `application.properties` reference

```properties
spring.application.name=gitpulse
server.port=8080

github.api.base-url=https://api.github.com
github.api.token=${GITHUB_API_TOKEN}

spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.redis.time-to-live=600000
```

---

## How Caching Works

First request to `/api/profile/NITHINKR06`:
```
Request → GitHubService → GitHub API (live call) → Redis stores result → Response
```

Any request within 10 minutes:
```
Request → GitHubService → Redis cache hit → Response (no GitHub API call)
```

This prevents hitting GitHub's 5000 req/hour rate limit on repeated lookups.

---

## Security

- GitHub token stored in `.env` — never committed
- `.gitignore` excludes both `.env` and `application.properties`
- Token scope limited to `public_repo` only
- No user data stored — all processing is stateless

---

## Author

**Nithin K R**
B.Tech Cybersecurity · NMAMIT, Nitte (2027)
Technical Lead @ CSI NMAMIT

[![GitHub](https://img.shields.io/badge/GitHub-NITHINKR06-black?style=flat-square&logo=github)](https://github.com/NITHINKR06)

---

## License

MIT © 2026 Nithin K R

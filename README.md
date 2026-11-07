GameMatch - Player MatchMaking Backend

    A Java and SpringBoot REST API that simulates a one on one multiplayer matchmaking system. Players can be registered, maintains matchmaking ranking or MMR, join queues and matches, get paired with players of similar MMR.

---

Features include:
-> Player Registration:Create Players with unique ID's and MMR
-> MMR Tracking:Track and store wins, losses and MMR
-> Matchmaking queues:Add/Remove players in queue
-> Skill-based matchmaking:Pair players with the closest MMR
-> MMR constraints:MMR differences can only be with a 0-100 point range
-> Input Validation:Invalid usernames and MMR gets rejected
-> Error Handling: Returns appropriate HTTP error responses
-> Automated Testing: JUnit 5 tests verify queue behavior, matchmaking accuracy registration and validation

---

---

Technologies:
-> Java 17
-> Spring Boot
-> Spring Web MVC
-> Maven
-> JUnit 5
-> REST APIs
-> Git and GitHub

---

---

How does MatchMaking work?
The matchmaking service compares players waiting in the queue by

1. Retrieving all players in queue
2. Compare all possible pairs
3. Calculating MMR differences
4. Rejecting pairs with differences greater than 100
5. Matching players with the smallest MMR difference
6. Creates matches and remove players from queue

---

API Endpoints

Method EndPoint Description

---

POST /players Register a player
GET /players Retrieve all players
POST /queue/{playerId} Join matchmaking queue
DELETE /queue/{playerId} Leave matchmaking queue
GET /queue View queued players
POST /matches/create Create the closest compatible match
GET /matches Retireve created matches

Example Requests

---

Register a player:
POST /players
{
"username": "Alex",
"MMR": 1475
}

Join the matchmaking queue
POST /queue/{41837a53-9e2f-410a-b5c7-d2ae95a9d860}

Create a match
POST /matches/create

This service creates a match with two compatible players

---

---

Pre-reqs:
-> Java 17
-> Maven

Start Application
windows
.\mvnw.cmd spring-boot:run

Run Automated Tests
windows
.\mvnw.cmd test

---

---

Testing

The project includes automated JUnit 5 tests covering:
-> Successful player registration.
-> Rejection of invalid player information.
-> Prevention of duplicate queue entries.
-> Successful matchmaking within the MMR limit.
-> Rejection of incompatible player pairs.
-> Selection of the closest compatible MMR pair.

---

---

Current Limitations
-> Player, queue, and match data are stored in memory and reset when the application restarts.
-> The current implementation is a single-process prototype and does not provide distributed queue coordination.
-> Matchmaking currently creates two-player matches.
-> The matching algorithm uses a brute-force pair comparison approach.

---

---

Future Improvements:
-> Integrate PostgreSQL for persistent storage.
-> Implement multiplayer matches with configurable team sizes.
-> Introduce more advanced matchmaking rating adjustments.
-> Improve scalability for large matchmaking queues.
-> Add concurrency protection and distributed matchmaking support.
-> Implement API documentation with Swagger/OpenAPI.
-> Add CI/CD automation using GitHub Actions.

---

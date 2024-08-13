# Games-Afoot-BE

## About
GamesAfoot is a web application allows users to participate in a scavenger hunt based on real-world 
locations. Users can input their starting location or use their current location and select 
the number of locations they want to visit within a specified radius. The app generates a 
list of locations using OpenAI and provides hints for the user to discover each location. 
As the user reaches each location, the next hint is revealed until the final destination is found.
*****Please note that OpenAI may generate unreal locations :) currently, the OpenAI prompt seems to
be effectively generating real locations, however our team plans to integrate a locations API in the future!**

## Team
- [**Jaime Mitchell**](https://github.com/JaimeMitchell) - Original concept, Backend
- [**Nina Sohn**](https://github.com/enigmatic-loop) - Backend
- [**Jenny Chen**](https://github.com/jennycodingnow) - Frontend
- [**Miranda Zhang**](https://github.com/yxzhang88) - Frontend

## Features
- **Location-Based Scavenger Hunts**: Users can start a scavenger hunt based on their current or specified location.
- **Hints for Locations**: The application generates hints for users to discover the next location in the hunt.
- **Progress Tracking**: Users can track their progress in the scavenger hunt, with a target location index storing where they left off.
- **Interactive Map**: Users will see their location on the map as well as any plotted markers for locations they have found.

## Stretch Goals
- **User Registration and Authentication**: Users can register, log in, and securely store their credentials.
- **Multi-player Option**: Users can start a game with other users and play together.
- **Automatic testing**: Github actions automatically run tests before merging new code into main branch.
- **Spring HATEOAS**: Implement Spring HATEOAS for a truly RESTful API

## Tech Stack
### Backend
- Java 21
- Spring Boot 3.3.2 
- Spring MVC (RESTful APIs)
- Spring Data JPA (Database access)
- PostgreSQL (Database)
- Maven (Build Tool)
- JUnit & Mockito (Testing)

### Frontend
- React
- Leaflet

## Getting Started
### 1. Clone the Repository: 
```
$ git clone https://github.com/yourusername/scavenger-hunt-app.git
$ cd scavenger-hunt-app
```
### 2. Backend Setup
#### A. Environment Variables
Set up your environment variables in a .env file:
```
POSTGRES_URL=postgresql://localhost:5432/gamesafoot
POSTGRES_USERNAME=your_db_username
POSTGRES_PASSWORD=your_db_password
OPENAI_KEY=your_openai_api_key
```
#### B. Application Properties
Export environment variables
```
$ export $(cat .env | xargs)
```

#### C. Build and Run the Backend
```
$ mvn clean install
$ mvn spring-boot:run
```
The backend will start on `http://localhost:8080`.

### 3. Frontend Setup (Optional)
   https://github.com/yxzhang88/Games-Afoot-FE

### 4. Testing
Run unit tests using JUnit:
```
$ mvn test
```
### 5. API Endpoints
#### Scavenger Hunt
- **POST /hunts**: Create a new scavenger hunt
- **POST /hunts/{id}/generate_locations**: Generate locations for the hunt
- **GET /hunts**: Get all hunts
- **GET /hunts/{id}/locations**: Get locations for a hunt
- **GET /hunts/allLocations**: Get locations from ALL hunts

#### Progress
- **POST /progress**: Create progress
- **GET /progress**: Get ALL progress
- **GET /progress/{id}**: Get progress by id
- **PATCH /progress/{id}/update-progress**: Increment target location index to next location
- **PATCH /progress/{id}/complete-game**: Change gameComplete to true
- **DELETE /progress/{id}**: Delete progress


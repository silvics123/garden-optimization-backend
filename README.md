# Garden Optimization Backend

A Spring Boot backend service for optimizing garden layouts using constraint programming.

## Features

- **Garden Layout Optimization**: Uses Choco Solver for constraint-based garden optimization
- **Simple Garden Planning**: Basic garden layout generation with target utilization
- **Crop Management**: Database-driven crop management with companion and antagonist relationships
- **User Registration & Authentication**: Secure user accounts with encrypted passwords
- **REST API**: RESTful endpoints for garden planning, crop management, and user registration
- **Database Integration**: H2 in-memory database for development, PostgreSQL support for production
- **Security**: Spring Security with BCrypt password encryption and input validation

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security** (Authentication & Authorization)
- **Choco Solver 4.10.14** (Constraint Programming)
- **H2 Database** (Development)
- **PostgreSQL** (Production)
- **Maven** (Build Tool)
- **SLF4J** (Logging)
- **BCrypt** (Password Encryption)

## API Endpoints

### Garden Planning
- `POST /api/garden/optimize` - Optimize garden layout using constraint programming
- `POST /api/garden/simple` - Create simple garden layout with target utilization
- `GET /api/garden/crops` - Get all available crops

### User Management
- `POST /api/users/register` - Register a new user account

### Garden Planning Request Format
```json
{
  "gardenWidth": 8,
  "gardenLength": 5,
  "crops": ["tomatoes", "carrots", "lettuce", "basil", "peppers"]
}
```

### User Registration Request Format
```json
{
  "userName": "johndoe",
  "password": "securepassword123",
  "email": "john@example.com"
}
```

### Garden Planning Response Format
```json
{
  "layout": [
    ["T", "T", "C", "C", "L"],
    [".", ".", ".", ".", "."],
    [".", ".", ".", ".", "."]
  ],
  "plantingInstructions": {
    "tomatoes": [{"x": 0, "y": 0}, {"x": 1, "y": 0}],
    "carrots": [{"x": 2, "y": 0}, {"x": 3, "y": 0}],
    "lettuce": [{"x": 4, "y": 0}]
  },
  "utilizationRate": 62.5
}
```

### User Registration Response Format
```json
{
  "id": 1,
  "userName": "johndoe",
  "email": "john@example.com",
  "createdAt": "2025-09-28T04:47:12.400",
  "updatedAt": "2025-09-28T04:47:12.400"
}
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on port 8085 by default

### Database Console
Access the H2 database console at: `http://localhost:8085/h2-console`
- JDBC URL: `jdbc:h2:mem:garden`
- Username: `sa`
- Password: (empty)

## Configuration

### Application Properties
Key configuration options in `application.properties`:
- `server.port=8085` - Server port
- `garden.utilization.target-percentage=70` - Target garden utilization percentage
- Database configuration for H2 and PostgreSQL

### Logging
Comprehensive logging configuration with:
- Console and file output
- Log rotation (10MB max, 7 days history)
- Debug level for application code
- SQL query logging for development

## Project Structure

```
src/
├── main/
│   ├── java/com/silvics/garden/
│   │   ├── Application.java
│   │   ├── config/
│   │   │   ├── GardenConfiguration.java
│   │   │   └── SecurityConfiguration.java
│   │   ├── controller/
│   │   │   ├── GardenPlanningController.java
│   │   │   ├── HelloController.java
│   │   │   └── UserController.java
│   │   ├── dto/
│   │   │   ├── GardenOptimizationRequest.java
│   │   │   └── UserRegistrationRequest.java
│   │   ├── model/
│   │   │   ├── Crop.java
│   │   │   ├── CropAntagonist.java
│   │   │   ├── CropCompanion.java
│   │   │   ├── Garden.java
│   │   │   ├── PlantingPlan.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── CropRepository.java
│   │   │   └── UserRepository.java
│   │   └── service/
│   │       ├── GardenOptimizerService.java
│   │       └── UserService.java
│   └── resources/
│       ├── application.properties
│       └── db/migration/
│           ├── data.sql
│           └── schema.sql
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.
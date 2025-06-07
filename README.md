# Money Transfer API

This is a Spring Boot application implementing a simple money transfer API. The API allows transferring money between cards and confirming operations.

## API Specification

The API is defined in the `MoneyTransferServiceSpecification.yaml` file, which follows the OpenAPI 3.0.0 standard.

### Endpoints

1. **POST /transfer/transfer** - Transfer money between cards
2. **POST /transfer/confirmOperation** - Confirm a transfer operation

## Running the Application

### Prerequisites

- Java 23
- Docker and Docker Compose

### Steps to Run

1. Clone the repository
2. Start the PostgreSQL database:
   ```
   docker-compose up -d
   ```
3. Run the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080/transfer`

## Testing the API

### Example Transfer Request

```json
POST /transfer/transfer
{
  "cardFromNumber": "1234567890123456",
  "cardFromValidTill": "12/25",
  "cardFromCVV": "123",
  "cardToNumber": "9876543210987654",
  "amount": {
    "value": 100,
    "currency": "RUB"
  }
}
```

### Example Confirmation Request

```json
POST /transfer/confirmOperation
{
  "operationId": "your-operation-id-from-transfer-response",
  "code": "0000"
}
```

Note: For testing purposes, the confirmation code is always "0000".

## Database

The application uses PostgreSQL as the database. Test data includes two cards:

1. Card Number: 1234567890123456, Valid Till: 12/25, CVV: 123, Balance: 10000
2. Card Number: 9876543210987654, Valid Till: 10/27, CVV: 456, Balance: 5000

## Technologies Used

- Spring Boot 3.4.3
- Spring Data JPA
- PostgreSQL
- Lombok
- Docker & Docker Compose

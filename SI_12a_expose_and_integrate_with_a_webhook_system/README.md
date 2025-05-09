# Webhook System Documentation

## Project Architecture

The application is built using the following technologies and patterns:

- **Framework**: Spring WebFlux (Reactive Stack)
- **Database**: MongoDB
- **Architecture Pattern**: Layered Architecture
  - Controllers (API Layer)
  - Services (Business Logic)
  - Repositories (Data Access)
- **Key Components**:
  - Webhook Registration System
  - Event Trigger System
  - RESTful API Endpoints

## API Documentation

**Important Notice Regarding Service Availability**:
The service operates on an on-demand basis. Prior to integration testing, please contact the service administrator to schedule service activation.
The service administrator will also provide you the public url where the service is hosted.

### Endpoints

#### 1. Register Webhook
- **Endpoint**: `/register`
- **Method**: POST
- **Content-Type**: application/json
- **Request Body Structure**:
```json
{
  "webhookUrl": "string",
  "eventType": "string"
}
```

**Response Structure**:
```json
{
  "uuid": "string (UUID)",
  "webhookRegisteredTime": "string (ISO-8601 format)",
  "webhookUrl": "string",
  "eventType": "string"
}
```

**Supported Event Types**:
- `LIST_CREATED`
- `LIST_MODIFIED`
- `LIST_DELETED`

**Example Request using Postman**:
1. Open Postman
2. Create a new POST request to `/register`
3. Set header `Content-Type: application/json`
4. Add request body:
```json
{
    "webhookUrl": "https://webhook.site/your-webhook-id",
    "eventType": "LIST_CREATED"
}
```

**Example Response**:
```json
{
  "uuid": "11ceeb5b-b60b-4e66-9f19-42697d5ae516",
  "webhookRegisteredTime": "2024-03-08T12:34:15Z",
  "webhookUrl": "https://webhook.site/your-webhook-id",
  "eventType": "LIST_CREATED"
}
```

#### 2. Ping (Test Webhooks)
- **Endpoint**: `/ping`
- **Method**: POST
- **Description**: Triggers all registered webhooks for testing purposes

**Example Request using Postman**:
1. Create a new POST request to `/ping`
2. No request body needed

### Webhook Event Payload

When an event is triggered (via ping), your registered endpoint will receive a POST request with the following payload structure:

```json
{
    "id": "UUID",
    "event": "EVENT_TYPE",
    "timestamp": "ISO-8601 timestamp"
}
```

#### 3. Unregister Webhook
- **Endpoint**: `/unregister`
- **Method**: DELETE
- **Content-Type**: application/json
- **Request Body Structure**:
```json
{
  "webhookUrl": "string",
  "eventType": "string"
}
```

**Supported Event Types**: (same as registration)
- `LIST_CREATED`
- `LIST_MODIFIED`
- `LIST_DELETED`

**Example Request using Postman**:
1. Create a new DELETE request to `/unregister`
2. Set header `Content-Type: application/json`
3. Add request body:
```json
{
    "webhookUrl": "https://webhook.site/your-webhook-id",
    "eventType": "LIST_CREATED"
}
```

- **Response**: No content (204 status code)

## Testing Your Integration

1. First, ensure you have a publicly accessible endpoint that can receive webhook POST requests
2. Register your webhook using the `/register` endpoint
3. Test the integration by calling the `/ping` endpoint
4. Your registered endpoint should receive webhook events for all registered event types
5. Delete webhook registrations by using the `/unregister` endpoint
6. Test that webhooks were unregistered by calling the `/ping` endpoint

## Error Handling

The API will return appropriate HTTP status codes:
- 200: Successful operation
- 400: Bad request (invalid payload)
- 500: Server error

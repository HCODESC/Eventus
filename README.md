# Event Booking System

A web application for managing events, booking tickets, generating unique ticket codes, and sending email reminders.

## Project Setup

### Backend Setup
- [x] Initialize a Spring Boot project with dependencies: Web, JPA, Security, Mail, Scheduler.
- [x] Configure database connection (e.g., PostgreSQL) in `application.properties`.
- [x] Set up authentication with JWT
- [ ] Implement role based access

### Frontend Setup
- [ ] Create an Angular/React project.
- [ ] Install libraries (e.g., Angular Material or Bootstrap for UI, HttpClient for API calls).

## Database Schema

### Create Database Tables
- [ ] **Users**: `user_id`, `username`, `password`, `email`, `role`.
- [ ] **Events**: `event_id`, `name`, `date`, `time`, `venue`, `description`, `total_tickets`, `available_tickets`.
- [ ] **TicketTypes**: `ticket_type_id`, `event_id`, `type_name`, `price`, `quantity_available`.
- [ ] **Bookings**: `booking_id`, `user_id`, `event_id`, `booking_date`, `total_cost`.
- [ ] **BookingDetails**: `booking_detail_id`, `booking_id`, `ticket_type_id`, `quantity`, `subtotal`.
- [ ] **Tickets**: `ticket_id`, `booking_detail_id`, `ticket_code`.

### Define Relationships
- [ ] One-to-Many: Users to Bookings.
- [ ] One-to-Many: Events to TicketTypes.
- [ ] One-to-Many: Bookings to BookingDetails.
- [ ] One-to-Many: BookingDetails to Tickets.

## Backend Development

### User Management
- [ ] Build user registration and login endpoints.
- [ ] Secure endpoints with JWT or session-based authentication.

### Event Management
- [ ] Implement CRUD operations for events (restricted to Admins).
- [ ] Create APIs to fetch event details and ticket availability.

### Booking System
- [ ] Develop an API to create bookings with ticket type selections and quantities.
- [ ] Update `available_tickets` in Events and `quantity_available` in TicketTypes after booking.
- [ ] Calculate `total_cost` based on ticket prices and quantities.

### Ticket Generation
- [ ] Generate unique ticket codes or QR codes for each ticket.
- [ ] Save ticket details in the Tickets table.

### Email Reminders
- [ ] Configure email sending with Spring Mail (e.g., SMTP setup).
- [ ] Use Spring Scheduler to send reminders (e.g., 24 hours before the event).

## Frontend Development

### User Interface
- [ ] Build components:
  - [ ] Event listing page.
  - [ ] Event details page.
  - [ ] Booking form.
  - [ ] User profile page (showing booking history).
  - [ ] Admin dashboard (for event management).

### API Integration
- [ ] Create services to:
  - [ ] Fetch event data.
  - [ ] Submit booking requests.
  - [ ] Handle user authentication.

### State Management
- [ ] Set up state management for user data, event selections, and bookings.

## Testing

### Unit Tests
- [ ] Backend: Test services and controllers (e.g., JUnit, Mockito).
- [ ] Frontend: Test components and services (e.g., Jasmine, Jest).

### Integration Tests
- [ ] Test flows like booking a ticket and receiving a confirmation email.

## Deployment

### Backend Deployment
- [ ] Deploy Spring Boot app to a cloud platform (e.g., Heroku, AWS).
- [ ] Set up a production database (e.g., PostgreSQL).

### Frontend Deployment
- [ ] Build and deploy the frontend to a static hosting service (e.g., Netlify, Vercel).

## Documentation

### API Documentation
- [ ] Document APIs using Swagger or Postman.

### Project Documentation
- [ ] Update README with:
  - [ ] Setup instructions.
  - [ ] Tech stack overview.
  - [ ] Deployment steps.

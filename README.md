# Shop Management - Simple Java EE Project

## Overview

Shop Management is a Java EE project designed to provide a comprehensive solution for managing a retail shop. It consists of both front-end and back-end components that work together to facilitate various shop management tasks.

### Front-end

The front-end of the Shop Management system provides a user-friendly interface for interacting with the system. It utilizes HTML, CSS, jQuery, and AJAX to create a dynamic and responsive web application.

### Back-end

The back-end of the Shop Management system is implemented using Java EE technologies. It runs on the Apache Tomcat server and provides server-side logic for handling data processing, business rules, and database interactions.

## Features

- **CRUD Operations**: Basic CRUD operations are supported for managing customers, items, orders, and other entities.
- **Validation**: Fundamental validations ensure data integrity and accuracy.
- **Error Handling**: Robust error and exception handling mechanisms gracefully handle unexpected scenarios.
- **Design Patterns**: Appropriate design patterns with layered architecture ensure maintainability and scalability.
- **CORS Handling**: CORS errors are effectively managed to allow seamless communication between the front-end and back-end.
- **Database**: MySQL is used as the database management system to store and manage data.
- **Connection Pooling**: Integration with JNDI allows for efficient connection pooling, enhancing performance and scalability.

## Project Structure

The project is organized into distinct components:

### Front-end

- `index.html`: Main entry point for the web application.
- `controllers/`: Directory for JavaScript files, including jQuery and AJAX functionality.
- `controllers/validations/`: Directory for validation controllers.
- `assets/`: Other resources used in the application, including Bootstrap, Font Awesome, and jQuery.

### Back-end

- `src/main/java`: Directory containing Java classes.
- `src/main/resources/schema`: Database schema.
- `src/main/webapp/WEB-INF/`: Configuration files for the Java EE application.

## Getting Started

To set up and run the Shop Management project locally, follow these steps:

1. Clone the repository.
2. Set up the back-end dependencies.
3. Configure the database connection.
4. Deploy the Java EE application on the Apache Tomcat server.

## Usage

Once the back-end application is running, users can seamlessly interact with the front-end. The system consists of four main pages: Customer, Items, Place Order, and Order Details. Users can navigate through these pages to perform various actions and tasks.

## Technologies Used

### Front-end

- jQuery (Version 3.3.1)
- Bootstrap (Version 5.3.2)
- Font Awesome (Version 6.4.2)

### Back-end

- Java EE
- Apache Tomcat (Version 9.0)

### Database

- MySQL Connector (Version 8.0.32)
- Java Naming and Directory Interface (JNDI)

## Development Tools

- Maven (Version 4.0.0)

## License

This project is licensed under the MIT License. See the [LICENSE.md](LICENSE.md) file for details.

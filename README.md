# Library Management System

## Overview
The **Library Management System** is a Java-based application that manages library users, books, and branches. It provides role-based access for **Admin**, **Librarian**, and **Members**, allowing them to manage library resources efficiently. The system uses Hibernate ORM for database management and follows an Object-Oriented Programming (OOP) approach.

## Features
- **User Authentication & Role Management:** Supports Admin, Librarian, and Member roles.
- **Library Branch Management:** Admin can manage multiple library branches.
- **Book Borrowing System:** Members can borrow books from library branches.
- **Database Configuration:** Uses Hibernate for ORM-based database interactions.
- **Command Line Interface:** Provides a simple text-based interface for user interactions.

## Technologies Used
- **Java** (Core logic and business functionalities)
- **Hibernate ORM** (Database handling)
- **Jakarta Persistence API (JPA)** (Entity management)
- **MySQL or H2 Database** (Data persistence)

## Project Structure
```
com.sasibhumaraju
│── Main.java              # Entry point of the application
│
├── model                  # Data models (Entities)
│   ├── Admin.java         # Admin entity extending AppUser
│   ├── AppUser.java       # Base user class
│   ├── Librarian.java     # Librarian entity
│   ├── Member.java        # Member entity
│   ├── LibraryBranch.java # Library branch entity
│   ├── Book.java          # Book entity
│   ├── BorrowedBook.java  # Borrowed book entity
│
├── service                # Business logic for each role
│   ├── AdminService.java  # Admin functionalities
│   ├── LibrarianService.java # Librarian functionalities
│   ├── MemberService.java # Member functionalities
│   ├── LoginService.java  # Login management
│
├── config                 # Configuration files
│   ├── DataBaseConfig.java # Hibernate database configuration
│
├── util                   # Utility classes
│   ├── Util.java          # Utility functions for login/logout
```

## Installation & Setup
### Prerequisites
- **Java JDK 11+**
- **Maven or Gradle**
- **MySQL or H2 Database**

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/sasibhumaraju/library-management.git
   cd library-management
   ```
2. Configure the database in `hibernate.cfg.xml` (if using MySQL).
3. Compile and run the application:
   ```sh
   javac -d bin $(find . -name "*.java")
   java -cp bin com.sasibhumaraju.Main
   ```
4. Follow the on-screen instructions to log in and manage the library.

## How It Works
1. On startup, the system initializes and loads sample data.
2. Users can log in as Admin, Librarian, or Member by entering their credentials.
3. Admins can manage branches and users.
4. Librarians manage books and library transactions.
5. Members can borrow and return books.

## Future Enhancements
- Implement a **GUI** using JavaFX or Spring Boot.
- Introduce an **email-based authentication system**.
- Add **REST API endpoints** for integration with frontend applications.

## License
This project is open-source and available under the **MIT License**.

---
Developed by **Sashi Kumar Raju Bhumaraju**


Java User Registration with MySQL (JDBC)

This project is a simple Java application for user registration that stores user data in a MySQL database using JDBC (Java Database Connectivity). It includes basic input validation, such as checking for empty fields and validating email format.

Features

Validate that name and email are not empty.

Simple email format validation using regular expressions.

Store user data in a MySQL database using JDBC.

Auto-incrementing user ID.

Prerequisites

Java Development Kit (JDK) 8 or higher

MySQL Server

MySQL JDBC Driver (mysql-connector-java)

IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code)

Create the database:

CREATE DATABASE javadb;
USE javadb;


Create the users table:

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100)
);

Future Improvements

Add password field with hashing for security.

Implement full CRUD operations (Create, Read, Update, Delete).

Build a GUI for better user experience.

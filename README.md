# p05_tour_Management System

## Project Overview

This project, "P05 Tour Management System," aims to develop a comprehensive platform for managing tour packages, bookings, and customer information. It consists of a React-based frontend for user interaction and a Spring Boot REST API backend to handle business logic and data persistence.

* **What:** A web-based application for streamlined tour package and booking management.
* **Why:** To provide an efficient and user-friendly system for tour operators and customers, enhancing the management of tours from creation to booking.
* **How:** Developed using React for the client-side interface and Spring Boot for a robust, scalable RESTful API, with data stored in MySQL.

## Project Structure

The project follows a modular structure to separate client and server concerns:

p05_tour_Management/
├── src/
│   ├── client/  # React application source code
│   └── backend/ # Spring Boot application source code
├── dep/         # Project dependencies (e.g., specific libraries not managed by package managers)
├── doc/         # Project documentation (e.g., design docs, API specs)
├── res/         # All static resources (e.g., images, static assets)
├── test/        # Unit and integration tests
├── .config/     # Local configurations (e.g., IDE settings, editor config)
├── build/       # All scripts related to build process (e.g., Docker compose files)
├── samples/     # Sample code or usage examples (if any)
├── tools/       # Scripts to automate tasks (e.g., build scripts, utility scripts)
├── README.md    # This file - project overview and setup instructions
├── LICENSE.md   # Project license details
├── CHANGELOG.md # Tracks software updates and bug fixes
└── AUTHORS.md   # Lists significant authors/contributors


## Getting Started

Follow these instructions to set up and run the project on your local machine.

### Prerequisites

Before you begin, ensure you have the following installed:

* **Git:** For cloning the repository and version control.
* **Node.js & npm:** For the React frontend. (Recommended LTS version)
* **Java Development Kit (JDK):** For the Spring Boot backend. (Recommended LTS version, e.g., Java 21)
* **Maven:** For building and running the Spring Boot backend.
* **MySQL Server:** For the project database.

### 1. Backend Setup (Spring Boot)

This section guides you through setting up and running the Spring Boot REST API.

#### 1.1 Local Database Setup (MySQL)

This project requires a MySQL database. Follow these steps:

1.  **Install MySQL Server:** Ensure you have a MySQL server installed and running on your local machine.
2.  **Create Database:** Create a new database named `p05_tourmgmtdb`.
    You can do this via MySQL Workbench, command line, or phpMyAdmin.
    ```sql
    CREATE DATABASE p05_tourmgmtdb;
    ```
3.  **Database User:** Ensure your local MySQL user has appropriate permissions to access `p05_tourmgmtdb`. The credentials for this user will be configured via environment variables.

#### 1.2 Environment Variables for Database Credentials

**IMPORTANT:** For security reasons, database credentials are NOT committed to Git. You must set them as environment variables on your local machine.

### Required Environment Variables:

* `DB_USERNAME`: Your MySQL database username for local development.
* `DB_PASSWORD`: Your MySQL database password for local development.
* (Optional) `DB_HOST`: Defaults to `localhost`. Set if your MySQL server is on a different host.
* (Optional) `DB_PORT`: Defaults to `3306`. Set if your MySQL server uses a different port.
* (Optional) `DB_NAME`: Defaults to `p05_tourmgmtdb`. You only need to set this if your database name is different from the default.

### How to Set Environment Variables (Windows):

1.  **Open System Properties:**
    * Press `Windows Key + R`, type `sysdm.cpl`, and press `Enter`.
    * Go to the `Advanced` tab and click `Environment Variables...`.
2.  **Create New User Variables:**
    * Under "User variables", click `New...`.
    * For `DB_USERNAME`:
        * **Variable name:** `DB_USERNAME`
        * **Variable value:** Enter your local MySQL username.
        * Click `OK`.
    * For `DB_PASSWORD`:
        * **Variable name:** `DB_PASSWORD`
        * **Variable value:** Enter your local MySQL password.
        * Click `OK`.
    * (Add optional variables if your local setup deviates from defaults.)
    * Click `OK` for each variable, then `OK` on all open windows.
3.  **Apply Changes:**
    * **Close and reopen any command prompt/Git Bash windows and your Eclipse IDE** for the new variables to be recognized.

#### 1.3 Running the Spring Boot Backend

1.  **Navigate to Backend Directory:**
    ```bash
    cd p05_tour_management/src/serverside
    ```
2.  **Install Dependencies (first time or after `pom.xml` changes):**
    ```bash
    mvn clean install
    ```
3.  **Run the Application:**

    * **Option 1: Using Maven (from Command Prompt/Terminal)**
        ```bash
        mvn spring-boot:run
        ```
    * **Option 2: Using Eclipse IDE**
        * **Ensure Eclipse is Restarted:** If you set environment variables system-wide, restart Eclipse to pick them up.
        * In Eclipse, locate your main Spring Boot application class (e.g., `BackendApplication.java` in `src/main/java/com.cdac.project.backend`).
        * Right-click on the file and select `Run As > Spring Boot App`.

    * The backend API will typically run on `http://localhost:8080`.

### 2. Frontend Setup (React)

This section guides you through setting up and running the React client-side application.

1.  **Navigate to Frontend Directory:**
    ```bash
    cd p05_tour_management/src/clientside
    ```
2.  **Install Dependencies:**
    ```bash
    npm install
    ```
3.  **Run the Application:**
    ```bash
    npm start
    ```
    * The React app will typically run on `http://localhost:3000`.

### 3. Running Both Client and Server

To run the full application:

1.  Open **two separate terminal windows** (or use your IDE's run configurations).
2.  In the first terminal, navigate to the **serverside** directory:
    ```bash
    cd p05_tour_management/src/serverside
    ```
    Then, run the backend application (e.g., `mvn spring-boot:run`).

3.  In the second terminal, navigate to the **clientside** directory:
    ```bash
    cd p05_tour_management/src/clientside
    ```
    Then, run the frontend application (e.g., `npm start`).
4.  Access the application in your browser at `http://localhost:3000`.

## Contribution Guidelines

This project adheres to the SCM guidelines provided. Please ensure you follow these practices:

1.  **Branching:**
    * Each member should create a **feature branch** for each feature or bug fix.
    * All commits for a task should be made in this feature branch.
    * Branch names should include an Issue ID and a short description (e.g., `ISSUE-123-user-login-feature`).
    * Feature branches should **not** be deleted after merging to aid traceability.

2.  **Commits:**
    * All commits should be descriptive and relevant to the task in the feature branch.

3.  **Pull Requests (Merge Requests):**
    * Once a task is complete in your feature branch, generate a **Pull Request** (PR) to merge into the `main` (or `master`) branch.
    * The designated Maintainer will review and accept/reject PRs.

4.  **Issue Tracking:**
    * Every development branch, commit, and merge **must** have a corresponding Issue entry in the project's issue tracker (on GitHub/GitLab).
    * Issues should have appropriate labels and be assigned to relevant team members.

## License

[Link to your LICENSE.md here, e.g., MIT License]

## Changelog

[Link to your CHANGELOG.md here]

## Authors

[Link to your AUTHORS.md here]
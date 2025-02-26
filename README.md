# Chase E-Banking Web Application

A comprehensive e-banking web application that allows users to efficiently manage their accounts, view transaction history, track check payments, and more. This project is designed with **Java EE**, **Jakarta Servlet API**, and **JSP** for a clean and secure online banking experience.

## Features

- **User Registration and Login:**
  - Users can register with a unique username, password, and email.
  - Secure login system with an option to remember the user for faster access.

- **Account Management:**
  - Create and manage multiple bank accounts.
  - View details of Main Accounts and Savings Accounts.
  - Retrieve account balances and transaction history.

- **Transaction Management:**
  - Record all incoming and outgoing transactions.
  - Search for transactions based on recipients or amount.
  - Retrieve transaction stats for reports.

- **Check Management:**
  - Manage checks tied to an account, including check issuance details (recipient, date, note, etc.).
  - View and search checks based on metadata.

- **Secure Persistence:**
  - Data is securely stored in a MySQL database.
  - Access through DAO classes for separation of concerns in persistence logic.

---

## Project Structure

The project follows a **Model-View-Controller (MVC)** pattern for a clean, maintainable architecture.

- **Model:** Handles the database schema and contains core business objects (`Account`, `Transaction`, `Check`, `User`).
- **View:** Contains JSP pages (`dashboard.jsp`, `register.jsp`, `error.jsp`) and a **CSS-based UI** for styling.
- **Controller:** Servlet classes (`DashboardServlet`, `RegisterServlet`, `LoginServlet`) manage the business logic and data fetching.

Key files include:

1. **Frontend Views**
   - `dashboard.jsp`: Displays account details, transactions, and checks for a logged-in user.
   - `register.jsp/index.jsp`: Registration and login forms.

2. **Backend Logic**
   - `RegisterServlet.java`: Manages user registration and initializes user accounts.
   - `DashboardServlet.java`: Retrieves and displays user-specific account, transaction, and check data.
   - `LoginServlet.java`: Handles login and session management.

3. **DAO Classes**
   - `UserDAO.java`: Operations for user authentication and registration.
   - `AccountDAO.java`: Manages account-related operations.
   - `TransactionDAO.java`: Fetches and stores transaction data.
   - `CheckDAO.java`: Handles operations pertaining to user-issued checks.

4. **Database Connection**
   - `DBConnector.java`: Provides MySQL connection management with auto-commit settings.

---

## Tech Stack

- **Frontend:**
  - JSP (Java Server Pages)
  - HTML, CSS for styling and user experience
  - Responsive design using `style.css`

- **Backend:**
  - Java EE with Jakarta Servlet API
  - MySQL for relational database management
  - Tomcat (WAR packaging)

- **Dependencies:**
  - `mysql-connector-java` for DB connectivity.
  - `junit-jupiter` for testing.

---

## Installation and Running the Application

### Prerequisites
- Java 23 or higher
- Maven
- MySQL (Database and user credentials are required)
- Apache Tomcat (for deployment)

### Steps to Run
1. Clone the repository.
   ```bash
   git clone https://github.com/yourusername/your-repository.git
   cd your-repository
   ```

2. Configure the database.
   - Create a database named `ChaseBank`.
   - Update the `DBConnector` class with your MySQL database connection string if necessary.

   Example connection string in `DBConnector`:
   ```java
   private static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/ChaseBank?user=root";
   ```

3. Build the project using Maven.
   ```bash
   mvn clean install
   ```

4. Deploy the WAR file to Tomcat.
   - Copy the generated `Ebanking.war` file (from `target/` directory) into the `webapps` folder of your Tomcat installation.
   - Start Tomcat and access the application at `http://localhost:8080/Ebanking`.

---

## Usage

1. **Register a new user:**
   - Visit the registration page (`register.jsp`).
   - Fill in the username, password, and email to create a new account.

2. **Log in as a user:**
   - Use the login page (`index.jsp`) to log in with your credentials.

3. **Navigate the dashboard:**
   - View account details, transactions, and checks.
   - Perform searches for specific transactions or check information.

---

## Key Highlights

- **Security:**
  - Secure password storage and robust session management.
  - Persistent session cookies for user convenience.

- **Modular Architecture:**
  - Decoupled `DAO` classes for database operations.
  - Services and servlets that aid in business logic handling.

- **Custom Error Handling:**
  - Proper redirections on invalid inputs or malfunctioning cases (`error.jsp`).

---

## Future Enhancements
- Add support for multi-currency accounts.
- Implement an admin dashboard for managing user accounts and detecting suspicious activity.
- Integrate an email notification system for transactions and account changes.
- Migrate to Spring Boot for modern dependency injection and simplified configurations.

---

## Contributing

Feel free to open issues or contribute via pull requests. All contributions are welcome!

1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes and open a pull request.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

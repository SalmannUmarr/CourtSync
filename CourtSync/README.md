# CourtSync SE

Standalone project isolating **User Registration**, **Secure Authentication**, and **Persistent Profile Storage** from the full CourtSync application.

## Features

- **User Registration (UC1)**: Create account with name, email, phone, and secure password. Duplicate email validation. JOptionPane for success/error messages.
- **Login (UC2)**: Verify credentials against CSV, role-based dashboard (Customer vs Admin). "Invalid username or password" on failure.
- **Password Security**: SHA-256 hashing with salt. Only hashed passwords stored in `data/users.csv`. Plain-text never written to logs.
- **Persistent Storage**: User data saved to `data/users.csv` and preserved after app closes.

## Requirements

- Java 11 or later

## How to Run

1. Open the project in your IDE or navigate to the project directory.
2. Ensure the working directory is the project root (so `data/` resolves correctly).
3. Run `com.courtsync.se.Main` (or `Main.java`).

### Command Line (from project root)

```bash
cd /path/to/CourtSync_SE
javac -d out com/courtsync/se/Main.java com/courtsync/se/controller/*.java com/courtsync/se/dao/*.java com/courtsync/se/dao/impl/*.java com/courtsync/se/datastore/*.java com/courtsync/se/model/*.java com/courtsync/se/security/*.java com/courtsync/se/ui/*.java
java -cp out com.courtsync.se.Main
```

Or use your IDE: set the project root as the working directory and run `com.courtsync.se.Main`.

## Default Admin (for testing)

On first run, if `data/users.csv` is empty, a default admin is created:
- **Email:** admin@courtsync.se
- **Password:** admin123

Use these credentials to test the Admin dashboard. The registration UI creates **Customer** accounts only.

## Project Structure

```
CourtSync_SE/
├── com/courtsync/se/
│   ├── Main.java                 # Entry point
│   ├── controller/
│   │   └── CourtSyncSystem.java   # Login, registration
│   ├── dao/
│   │   ├── FileHandler.java      # data/ folder I/O
│   │   ├── UserDAO.java
│   │   └── impl/UserDAOImpl.java
│   ├── datastore/
│   │   └── DataStore.java        # In-memory users, loads from CSV
│   ├── model/
│   │   ├── User.java
│   │   ├── Customer.java
│   │   └── Administrator.java
│   ├── security/
│   │   ├── PasswordHasher.java   # SHA-256 + salt
│   │   └── InputValidator.java
│   └── ui/
│       ├── MainFrame.java
│       ├── LoginPanel.java
│       ├── RegistrationPanel.java
│       ├── CustomerDashboardPanel.java
│       ├── AdminDashboardPanel.java
│       └── UIStyles.java
├── data/
│   └── users.csv                 # User persistence (created on first run)
└── README.md
```

## Security Notes

- Passwords are hashed with SHA-256, salt, and 10,000 iterations.
- Plain-text passwords are never written to logs or the CSV file.
- Constant-time comparison used in password verification to mitigate timing attacks.

# 🔍 Digital Lost & Found System

> A Java desktop application to report, search, and manage lost and found items.  
> Built with Java Swing + MySQL + JDBC following MVC Architecture.

![Java](https://img.shields.io/badge/Java-JDK%2025-orange?style=flat-square&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.9.13-red?style=flat-square&logo=apachemaven)
![Status](https://img.shields.io/badge/Status-65%25%20Complete-yellow?style=flat-square)

---

## 📸 Screens

| Login | Dashboard | Browse Items |
|-------|-----------|--------------|
| User login with validation | 4 navigation cards | JTable with Lost/Found filter |

---

## 🏗️ Architecture
```
GUI (Swing) → Controller → Service → Repository → MySQL
                              ↓
                           Model (User, Item, LostItem, FoundItem)
                              ↓
                           Util (CustomException, FileLogger)
```

---

## 📁 Folder Structure
```
src/main/java/com/lostfound/
├── model/        → User, Item (abstract), LostItem, FoundItem
├── controller/   → AuthController, ItemController
├── service/      → UserService, ItemService
├── repository/   → UserRepository, ItemRepository
├── database/     → DBConnection (JDBC Singleton)
├── gui/          → LoginFrame, RegisterFrame, DashboardFrame,
│                   ReportLostItemFrame, ReportFoundItemFrame, BrowseItemsFrame
└── util/         → CustomException, FileLogger
```

---

## 🗄️ Database Setup

Run this in MySQL:
```sql
CREATE DATABASE lostfound_db;
USE lostfound_db;

CREATE TABLE users (
    user_id   INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL,
    phone     VARCHAR(20),
    password  VARCHAR(100) NOT NULL
);

CREATE TABLE items (
    item_id       INT PRIMARY KEY AUTO_INCREMENT,
    item_name     VARCHAR(100) NOT NULL,
    category      VARCHAR(50),
    description   TEXT,
    location      VARCHAR(100),
    date_reported DATE,
    status        VARCHAR(10),
    user_id       INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE messages (
    message_id  INT PRIMARY KEY AUTO_INCREMENT,
    sender_id   INT,
    receiver_id INT,
    item_id     INT,
    message_text TEXT,
    FOREIGN KEY (sender_id)   REFERENCES users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id)     REFERENCES items(item_id)
);
```

Then open `DBConnection.java` and set your MySQL password:
```java
private static final String PASSWORD = "your_mysql_password_here";
```

---

## ▶️ How to Run

Open terminal in VS Code **(use CMD, not PowerShell)**:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.lostfound.gui.LoginFrame"
```

---

## ✅ Features

- [x] User Registration & Login with input validation
- [x] Report Lost Items — saved to MySQL
- [x] Report Found Items — saved to MySQL
- [x] Browse all items with JTable + Lost/Found filter
- [x] MVC Architecture — clean separation of layers
- [x] Abstract class `Item` → extended by `LostItem` and `FoundItem`
- [x] Custom checked Exception — `CustomException`
- [x] File Logger — logs all events to `lostfound_log.txt`

### 🔲 Coming Soon
- [ ] User Profile Screen
- [ ] Messaging system between users
- [ ] Multithreading for background item loading
- [ ] Stream API & Java 8 features demo
- [ ] MouseListener on JTable rows

---

## 🎓 OOP Concepts (Syllabus Coverage)

| Unit | Topic | Implementation |
|------|-------|----------------|
| Unit 1 | Classes, Objects, Constructors, Access Modifiers | `User.java`, `Item.java` |
| Unit 2 | Strings, Methods, Validation | `UserService.java` |
| Unit 3 | Inheritance, super(), Method Overriding | `Item` → `LostItem`, `FoundItem` |
| Unit 4 | Abstract Classes, Anonymous Classes | `Item.java`, GUI ActionListeners |
| Unit 5 | ArrayList, Custom Exception, File I/O | Repositories, `CustomException`, `FileLogger` |
| Unit 6 | Java Swing, JDBC, Lambda Expressions | All GUI screens, `DBConnection` |

---

## 📦 Dependencies (`pom.xml`)
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

---

## 📝 Notes

- MySQL must be running before launching the app
- Log file is auto-created as `lostfound_log.txt` in the project root
- Java 17+ required (compiler target set to 17 in `pom.xml`)

---

> 📍 Mid-Semester Project — Vishwakarma Institute of Technology, Pune  
> Department of Information Technology

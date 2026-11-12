# Student Management System – Java

A simple console-based **Student Management System** created for **CodeMax Digital Internship – Module 02: Methods, Collections & Exceptions**.

## Module 02 Concepts Covered

### 1. Methods
- Creating and calling methods
- Parameters
- Return values
- Method overloading
- Variable scope

### 2. Collections
- `ArrayList` – stores the student records
- `HashMap` – maps student ID to student object for quick search/update/delete
- `HashSet` – stores unique course names

### 3. Exception Handling
- `try-catch`
- `throw`
- `throws`
- Custom `StudentNotFoundException`
- `NumberFormatException`
- `IllegalArgumentException`

### 4. File Handling
- Reads saved student records when the program starts
- Writes student records to `students.txt`
- Uses `BufferedReader` and `BufferedWriter`

## Features

1. Add student
2. View all students
3. Search student by ID
4. Update student details
5. Delete student
6. View unique courses
7. Save data manually
8. Save data automatically when exiting

## Requirements

- Java JDK 17 or later
- VS Code, IntelliJ IDEA, Eclipse, or any Java-supported IDE

## How to Run

### Using Terminal

Open the project folder and run:

```bash
javac StudentManagementSystem.java
java StudentManagementSystem
```

### Using VS Code

1. Open this folder in VS Code.
2. Make sure the Java extension pack is installed.
3. Open `StudentManagementSystem.java`.
4. Click **Run**.
5. Use the console menu to manage students.

## Data Storage

The program automatically creates:

```text
students.txt
```

The file stores student records in this format:

```text
101|Ashwin|CSE|Java|85.5
102|Bhuvanesh|CSE|Python|90.0
```

The data is loaded again when the program is restarted.

## Example Menu

```text
======================================
       STUDENT MANAGEMENT SYSTEM
======================================
1. Add Student
2. View All Students
3. Search Student
4. Update Student
5. Delete Student
6. View Unique Courses
7. Save Data
8. Exit
======================================
```

## Suggested Test Data

Use these sample records:

| ID | Name | Department | Course | Mark |
|---|---|---|---|---:|
| 101 | Ashwin | CSE | Java | 85 |
| 102 | Bhuvanesh | CSE | Python | 90 |
| 103 | Rahul | IT | Java | 78 |

After adding these records, use **View Unique Courses**. The `HashSet` will show each course only once.

## Project Structure

```text
Student-Management-System/
├── StudentManagementSystem.java
├── README.md
├── .gitignore
└── students.txt
```

`students.txt` is generated automatically by the application and is ignored by Git.

## Learning Outcome

This project demonstrates how Java methods and collection classes can be combined to build a practical console application. It also demonstrates exception handling and file persistence so that student records can be saved and loaded between program runs.

## Author

**Ashwin K**

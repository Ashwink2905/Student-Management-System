import java.io.*;
import java.util.*;

/**
 * Simple console-based Student Management System.
 *
 * Module 02 concepts demonstrated:
 * - Methods and parameter passing
 * - Method overloading
 * - Scope of variables
 * - ArrayList
 * - HashMap
 * - HashSet
 * - try-catch, throw and throws
 * - File reading and writing
 */
public class StudentManagementSystem {

    private static final Scanner scanner = new Scanner(System.in);

    // ArrayList: maintains the collection of student objects.
    private static final ArrayList<Student> students = new ArrayList<>();

    // HashMap: provides fast lookup of a student using ID.
    private static final HashMap<Integer, Student> studentMap = new HashMap<>();

    // HashSet: stores unique course names.
    private static final HashSet<String> courses = new HashSet<>();

    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadFromFile();

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> showCourses();
                    case 7 -> saveToFile();
                    case 8 -> {
                        saveToFile();
                        System.out.println("Thank you for using Student Management System!");
                    }
                    default -> System.out.println("Invalid choice. Please select 1 to 8.");
                }
            } catch (StudentNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

        } while (choice != 8);
    }

    // Method
    private static void displayMenu() {
        System.out.println("\n======================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. View Unique Courses");
        System.out.println("7. Save Data");
        System.out.println("8. Exit");
        System.out.println("======================================");
    }

    // Method with multiple parameters
    private static void addStudent() {
        int id = readInt("Enter student ID: ");

        if (studentMap.containsKey(id)) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        String name = readText("Enter student name: ");
        String department = readText("Enter department: ");
        String course = readText("Enter course: ");
        double mark = readDouble("Enter mark (0-100): ");

        validateMark(mark);

        Student student = new Student(id, name, department, course, mark);

        students.add(student);              // ArrayList
        studentMap.put(id, student);        // HashMap
        courses.add(course);                // HashSet

        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n--------------- STUDENTS ---------------");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() throws StudentNotFoundException {
        int id = readInt("Enter student ID to search: ");

        // HashMap lookup
        Student student = studentMap.get(id);

        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + id + " was not found.");
        }

        System.out.println("\nStudent Found:");
        System.out.println(student);
    }

    private static void updateStudent() throws StudentNotFoundException {
        int id = readInt("Enter student ID to update: ");

        Student student = studentMap.get(id);

        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + id + " was not found.");
        }

        System.out.println("Leave a field blank to keep its current value.");

        String name = readOptionalText("New name [" + student.getName() + "]: ");
        String department = readOptionalText("New department [" + student.getDepartment() + "]: ");
        String course = readOptionalText("New course [" + student.getCourse() + "]: ");

        if (!name.isEmpty()) {
            student.setName(name);
        }

        if (!department.isEmpty()) {
            student.setDepartment(department);
        }

        if (!course.isEmpty()) {
            student.setCourse(course);
            courses.add(course);
        }

        String markInput = readOptionalText(
                "New mark [" + student.getMark() + "] (leave blank to keep): ");

        if (!markInput.isEmpty()) {
            double mark;
            try {
                mark = Double.parseDouble(markInput);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Mark must be a valid number.");
            }

            validateMark(mark);
            student.setMark(mark);
        }

        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() throws StudentNotFoundException {
        int id = readInt("Enter student ID to delete: ");

        Student student = studentMap.remove(id);

        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + id + " was not found.");
        }

        students.remove(student);

        // Rebuild unique course set from remaining students.
        rebuildCourses();

        System.out.println("Student deleted successfully.");
    }

    private static void showCourses() {
        rebuildCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("\nUnique Courses:");
        for (String course : courses) {
            System.out.println("- " + course);
        }
    }

    private static void rebuildCourses() {
        courses.clear();

        for (Student student : students) {
            courses.add(student.getCourse());
        }
    }

    // Overloaded method 1
    private static void printMessage(String message) {
        System.out.println(message);
    }

    // Overloaded method 2
    private static void printMessage(String message, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static String readOptionalText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static void validateMark(double mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100.");
        }
    }

    /*
     * File writing.
     * throws IOException demonstrates checked-exception declaration.
     */
    private static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Student student : students) {
                writer.write(student.toFileFormat());
                writer.newLine();
            }

            printMessage("Student data saved successfully.");

        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    /*
     * File reading.
     * Existing data is loaded when the program starts.
     */
    private static void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Student student = Student.fromFileFormat(line);

                    students.add(student);
                    studentMap.put(student.getId(), student);
                    courses.add(student.getCourse());

                } catch (Exception e) {
                    System.out.println("Skipped invalid record: " + line);
                }
            }

            System.out.println("Previous student data loaded successfully.");

        } catch (IOException e) {
            System.out.println("Could not load data: " + e.getMessage());
        }
    }

    // Student class
    static class Student {
        private int id;
        private String name;
        private String department;
        private String course;
        private double mark;

        public Student(int id, String name, String department, String course, double mark) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.course = course;
            this.mark = mark;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public double getMark() {
            return mark;
        }

        public void setMark(double mark) {
            this.mark = mark;
        }

        // Convert object to a simple file format.
        public String toFileFormat() {
            return id + "|" + clean(name) + "|" + clean(department) + "|"
                    + clean(course) + "|" + mark;
        }

        // Convert a file record back to a Student object.
        public static Student fromFileFormat(String line) {
            String[] parts = line.split("\\|", -1);

            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid student record.");
            }

            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String department = parts[2];
            String course = parts[3];
            double mark = Double.parseDouble(parts[4]);

            return new Student(id, name, department, course, mark);
        }

        private static String clean(String value) {
            return value.replace("|", "/");
        }

        @Override
        public String toString() {
            return String.format(
                    "ID: %d | Name: %s | Department: %s | Course: %s | Mark: %.2f",
                    id, name, department, course, mark
            );
        }
    }

    // Custom exception using throw/throws.
    static class StudentNotFoundException extends Exception {
        public StudentNotFoundException(String message) {
            super(message);
        }
    }
}

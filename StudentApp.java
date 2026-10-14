import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentApp {
    private final ArrayList<Student> students;
    private final FileManager fileManager;

    private StudentApp(ArrayList<Student> students, FileManager fileManager) {
        this.students = students;
        this.fileManager = fileManager;
    }

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        ArrayList<Student> students;
        try {
            students = fileManager.loadStudents();
        } catch (IOException exception) {
            System.err.println("Could not load students.txt: " + exception.getMessage());
            return;
        }

        StudentApp app = new StudentApp(students, fileManager);
        try (Scanner scanner = new Scanner(System.in)) {
            app.run(scanner);
        }
    }

    private void run(Scanner scanner) {
        System.out.println("Loaded " + students.size() + " student(s).");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextLine()) {
                System.out.println("\nInput ended. Exiting Student Management System.");
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    running = addStudent(scanner);
                    break;
                case "2":
                    viewStudents();
                    break;
                case "3":
                    running = searchStudent(scanner);
                    break;
                case "4":
                    running = false;
                    System.out.println("Thank you for using the Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please enter a number from 1 to 4.");
            }
        }
    }

    private boolean addStudent(Scanner scanner) {
        Integer id = readPositiveInteger(scanner, "Enter student ID: ");
        if (id == null) {
            return endForInput(scanner);
        }
        if (findStudent(id) != null) {
            System.out.println("A student with ID " + id + " already exists.");
            return true;
        }

        String name = readName(scanner);
        if (name == null) {
            return endForInput(scanner);
        }
        Integer age = readPositiveInteger(scanner, "Enter student age: ");
        if (age == null) {
            return endForInput(scanner);
        }

        Student student = new Student(id, name, age);
        try {
            if (!fileManager.saveStudent(student)) {
                System.out.println("A student with ID " + id + " already exists in students.txt.");
                return true;
            }
        } catch (IOException exception) {
            System.err.println("Could not save student: " + exception.getMessage());
            return true;
        }

        students.add(student);
        System.out.println("Student added and saved successfully.");
        return true;
    }

    private boolean searchStudent(Scanner scanner) {
        Integer id = readPositiveInteger(scanner, "Enter student ID to search: ");
        if (id == null) {
            return endForInput(scanner);
        }

        Student student = findStudent(id);
        if (student == null) {
            System.out.println("No student found with ID " + id + ".");
        } else {
            System.out.println("Student found: " + student);
        }
        return true;
    }

    private void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students have been added yet.");
            return;
        }

        System.out.println("Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private Integer readPositiveInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }

            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive integer.");
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private String readName(Scanner scanner) {
        while (true) {
            System.out.print("Enter student name: ");
            if (!scanner.hasNextLine()) {
                return null;
            }

            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name must not be blank.");
            } else if (name.contains(",")) {
                System.out.println("Name must not contain a comma because commas separate CSV fields.");
            } else {
                return name;
            }
        }
    }

    private Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private boolean endForInput(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput ended. Exiting Student Management System.");
            return false;
        }
        return true;
    }
}

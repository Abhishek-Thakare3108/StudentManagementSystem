import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "students.txt";

    public ArrayList<Student> loadStudents() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    Student student = Student.fromFileString(line);
                    if (containsId(students, student.getId())) {
                        System.err.println("Skipping duplicate student ID on line " + lineNumber + ".");
                    } else {
                        students.add(student);
                    }
                } catch (IllegalArgumentException exception) {
                    System.err.println("Skipping invalid record on line " + lineNumber + ": "
                            + exception.getMessage());
                }
            }
        }
        return students;
    }

    public boolean saveStudent(Student student) throws IOException {
        ArrayList<Student> savedStudents = loadStudents();
        if (containsId(savedStudents, student.getId())) {
            return false;
        }

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(student.toFileString());
            writer.write(System.lineSeparator());
        }
        return true;
    }

    private boolean containsId(ArrayList<Student> students, int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return true;
            }
        }
        return false;
    }
}

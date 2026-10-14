# File-Based Persistent Student Management System

**Java Developer – Task 2**  
**Maincrafts Technology**

## Project overview

The File-Based Persistent Student Management System is a beginner-friendly Java console application for adding, viewing, and searching student records. It keeps records in a local text file, so saved students are available again after the application is closed and restarted.

The project separates student data, file persistence, and the interactive menu into individual Java classes. It uses Core Java only; no database, framework, or external dependency is required.

## Objectives and key features

- Practice object-oriented programming with a `Student` model.
- Add student records with an ID, name, and age.
- View all loaded students and search by student ID.
- Persist each successfully added student to `students.txt`.
- Load existing records on application startup.
- Validate menu choices and student input.
- Prevent duplicate student IDs.
- Skip and report invalid or duplicate records encountered in the data file.
- Handle file I/O errors with explicit messages.

## Technologies used

- Core Java
- `ArrayList` for the in-memory student list
- `FileReader` and `BufferedReader` for reading records
- `FileWriter` in append mode for saving records
- Try-with-resources for file and console resource handling
- Java exception handling for invalid records and I/O errors

No external libraries or build tools are needed.

## Project folder structure

```text
StudentManagementSystem/
├── Student.java
├── FileManager.java
├── StudentApp.java
├── README.md
└── students.txt
```

`students.txt` is the local data file. It is created on the first successful save if it does not already exist. The submitted workspace currently includes this file.

## Prerequisites

- A Java Development Kit (JDK) with `javac` and `java` available from the terminal.
- VS Code is suitable for editing and running the project; the Java Extension Pack is optional.

Check that Java is available by running:

```text
javac -version
java -version
```

## Compile and run

Open the project folder in VS Code, then open its integrated terminal. Ensure the terminal's current directory is the folder containing the three `.java` files. Compile and launch with:

```text
javac Student.java FileManager.java StudentApp.java
java StudentApp
```

The application reads and writes `students.txt` relative to the current working directory. Run `java StudentApp` from the project folder to use the `students.txt` located beside the source files.

## Source and data files

- **`Student.java`** defines a student with an ID, name, and age. It provides getters, a readable `toString()`, and methods to serialize a student to one CSV line or parse a CSV line back into a student. Invalid field counts, non-integer numbers, non-positive IDs or ages, and blank names are rejected as invalid records.
- **`FileManager.java`** loads records from `students.txt` with `FileReader` and `BufferedReader`, and saves a record with `FileWriter` in append mode. It returns an empty list when the file is missing, reports and skips invalid or repeated IDs in the file, and checks existing records before saving to prevent duplicate IDs. File I/O exceptions are passed to the application for an error message.
- **`StudentApp.java`** loads students at startup and provides the console menu: Add Student, View Students, Search Student by ID, and Exit. It validates user input, maintains the in-memory `ArrayList<Student>`, and reports save or load errors.
- **`students.txt`** stores one student per line. The application creates it automatically when a student is first saved if it is absent. The file is plain text and can be inspected with a text editor.

## CSV storage format

Each line uses three comma-separated fields in this order:

```text
id,name,age
```

For example:

```text
1042,Meera Sharma,19
```

The name cannot be blank or contain a comma; a comma is the field delimiter and the parser expects exactly three fields. Names may contain spaces.

## Data persistence check

To confirm that records remain available after restarting:

1. Compile and run the application from the project folder.
2. Choose **1. Add Student** and enter a positive ID, a name, and a positive age.
3. Add another student if desired, then choose **4. Exit**.
4. Optionally inspect `students.txt` and confirm that the new records appear as CSV lines.
5. Run `java StudentApp` again. The startup message should report the saved student count.
6. Choose **2. View Students** or **3. Search Student by ID** to confirm the saved records were loaded.

New entries are appended, rather than overwriting previously saved lines.

## Validation and exception handling

- Menu choices outside `1` through `4` are rejected with a retry message.
- IDs and ages must be positive integers. Non-numeric and non-positive values are rejected and prompted again.
- Names are trimmed and cannot be blank or contain commas.
- An ID already present in the loaded list or saved file cannot be added again.
- Invalid or corrupted file lines are reported with their line number and skipped; valid lines can still load.
- File read and write failures are reported to the user. If the initial file load fails, the application reports the error and stops instead of starting with misleading empty data.
- Try-with-resources closes the file reader, writer, and input scanner.

## Testing performed and expected results

The application was compiled with the commands above and launched from the project folder. Scripted console input was used to check:

- Invalid menu choices and invalid/non-positive ID and age input produce validation messages.
- Blank names are rejected.
- An empty student list produces a helpful message.
- Two students can be added and displayed; a repeated ID is rejected.
- Searching for an existing ID reports the student; searching for a missing ID reports that no student was found.
- The application displays its exit message.
- On a subsequent launch, previously saved test records load and can be viewed/searched.
- Invalid/corrupted lines and a repeated ID in the data file are reported and skipped without terminating the application.

The persistence interaction was run using an isolated temporary data directory to avoid replacing the workspace's existing `students.txt` records. No automated unit-test suite is included.

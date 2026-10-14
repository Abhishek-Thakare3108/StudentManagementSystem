public class Student {
    private final int id;
    private final String name;
    private final int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toFileString() {
        return id + "," + name + "," + age;
    }

    public static Student fromFileString(String line) {
        String[] fields = line.split(",", -1);
        if (fields.length != 3) {
            throw new IllegalArgumentException("Expected exactly three comma-separated fields.");
        }

        try {
            int id = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            int age = Integer.parseInt(fields[2].trim());

            if (id <= 0 || age <= 0 || name.isEmpty()) {
                throw new IllegalArgumentException("ID and age must be positive, and name must not be blank.");
            }
            return new Student(id, name, age);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("ID and age must be valid integers.", exception);
        }
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}

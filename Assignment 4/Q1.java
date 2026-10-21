import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q1 {
    public static void main(String[] args) {
        RegistrationPortal portal = new RegistrationPortal();

        portal.addCourse(new Course("CS101", "Programming Fundamentals", 2, new ArrayList<>()));
        portal.addCourse(new Course("CS201", "Data Structures", 2, List.of("CS101")));
        portal.addCourse(new Course("CS301", "Algorithms", 1, List.of("CS101", "CS201")));

        portal.markCompleted("S1", "CS101");
        portal.markCompleted("S2", "CS101");
        portal.markCompleted("S3", "CS101");
        portal.markCompleted("S1", "CS201");

        portal.register("S1", "CS101");
        portal.register("S2", "CS101");
        portal.register("S3", "CS101");
        portal.register("S4", "CS101");
        portal.register("S1", "CS101");

        portal.register("S1", "CS201");
        portal.register("S2", "CS201");
        portal.register("S4", "CS201");

        portal.register("S1", "CS301");
        portal.register("S9", "CS999");

        System.out.println();
        portal.printCourse("CS101");

        System.out.println("\n-- S1 drops CS101 --");
        portal.drop("S1", "CS101");
        portal.printCourse("CS101");

        System.out.println("\n-- S2 drops CS101 --");
        portal.drop("S2", "CS101");
        portal.printCourse("CS101");

        System.out.println();
        portal.printCourse("CS201");
        portal.printCourse("CS301");
    }
}

class Course {
    private final String code;
    private final String name;
    private final int capacity;
    private final Set<String> enrolled = new HashSet<>();
    private final List<String> prerequisites;
    private final LinkedList<String> waitlist = new LinkedList<>();

    Course(String code, String name, int capacity, List<String> prerequisites) {
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        this.prerequisites = new ArrayList<>(prerequisites);
    }

    String getCode() { return code; }
    String getName() { return name; }
    int getCapacity() { return capacity; }
    Set<String> getEnrolled() { return enrolled; }
    List<String> getPrerequisites() { return prerequisites; }
    LinkedList<String> getWaitlist() { return waitlist; }

    boolean isFull() {
        return enrolled.size() >= capacity;
    }
}

class RegistrationPortal {
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, Set<String>> completed = new HashMap<>();

    void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    void markCompleted(String studentId, String courseCode) {
        completed.computeIfAbsent(studentId, k -> new HashSet<>()).add(courseCode);
    }

    boolean register(String studentId, String courseCode) {
        Course course = courses.get(courseCode);
        if (course == null) {
            System.out.println(studentId + ": course " + courseCode + " not found");
            return false;
        }
        if (course.getEnrolled().contains(studentId)) {
            System.out.println(studentId + ": already enrolled in " + courseCode);
            return false;
        }
        if (course.getWaitlist().contains(studentId)) {
            System.out.println(studentId + ": already on waitlist for " + courseCode);
            return false;
        }
        Set<String> done = completed.getOrDefault(studentId, new HashSet<>());
        for (String pre : course.getPrerequisites()) {
            if (!done.contains(pre)) {
                System.out.println(studentId + ": missing prerequisite " + pre + " for " + courseCode);
                return false;
            }
        }
        if (!course.isFull()) {
            course.getEnrolled().add(studentId);
            System.out.println(studentId + ": enrolled in " + courseCode);
        } else {
            course.getWaitlist().addLast(studentId);
            System.out.println(studentId + ": " + courseCode + " is full, added to waitlist (position "
                    + course.getWaitlist().size() + ")");
        }
        return true;
    }

    boolean drop(String studentId, String courseCode) {
        Course course = courses.get(courseCode);
        if (course == null) {
            return false;
        }
        if (course.getEnrolled().remove(studentId)) {
            System.out.println(studentId + ": dropped " + courseCode);
            promoteFromWaitlist(course);
            return true;
        }
        if (course.getWaitlist().remove(studentId)) {
            System.out.println(studentId + ": removed from waitlist of " + courseCode);
            return true;
        }
        System.out.println(studentId + ": not enrolled in " + courseCode);
        return false;
    }

    private void promoteFromWaitlist(Course course) {
        while (!course.isFull() && !course.getWaitlist().isEmpty()) {
            String next = course.getWaitlist().pollFirst();
            course.getEnrolled().add(next);
            System.out.println(next + ": promoted from waitlist to " + course.getCode());
        }
    }

    void printCourse(String courseCode) {
        Course c = courses.get(courseCode);
        if (c == null) {
            System.out.println("Course " + courseCode + " not found");
            return;
        }
        System.out.println(c.getCode() + " - " + c.getName() + " (capacity " + c.getCapacity() + ")");
        System.out.println("  Prerequisites: " + c.getPrerequisites());
        System.out.println("  Enrolled     : " + new java.util.TreeSet<>(c.getEnrolled()));
        System.out.println("  Waitlist     : " + c.getWaitlist());
    }
}

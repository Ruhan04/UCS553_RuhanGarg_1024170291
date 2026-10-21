import java.util.Objects;
import java.util.Optional;

class Result<T extends Number> {
    private final String studentName;
    private final T marks;
    private final T totalMarks;

    public Result(String studentName, T marks, T totalMarks) {

        this.studentName = Objects.requireNonNull(studentName, "Student name must not be null");
        this.totalMarks = Objects.requireNonNull(totalMarks, "Total marks must not be null");
        this.marks = marks;
    }

    public Optional<T> getMarks() {
        return Optional.ofNullable(marks);
    }

    public double getPercentage() {
        double m = getMarks().map(Number::doubleValue).orElse(0.0);
        return (m / totalMarks.doubleValue()) * 100;
    }

    public boolean hasPassed(double threshold) {
        return getPercentage() >= threshold;
    }

    public void display(double threshold) {
        System.out.println("Student : " + studentName);
        getMarks().ifPresentOrElse(
            m -> {
                System.out.println("Marks   : " + m + " / " + totalMarks);
                System.out.printf("Percent : %.2f%%%n", getPercentage());
                System.out.println("Status  : " + (hasPassed(threshold) ? "PASS" : "FAIL"));
            },
            () -> System.out.println("Marks not available")
        );
    }
}

public class Q3 {
    public static void main(String[] args) {
        double passThreshold = 40.0;

        Result<Integer> r1 = new Result<>("Ruhan", 78, 100);
        r1.display(passThreshold);
        System.out.println();

        Result<Double> r2 = new Result<>("Aman", 32.5, 100.0);
        r2.display(passThreshold);
        System.out.println();

        Result<Integer> r3 = new Result<>("Neha", null, 100);
        r3.display(passThreshold);
        System.out.println();

        try {
            new Result<Integer>(null, 50, 100);
        } catch (NullPointerException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
}

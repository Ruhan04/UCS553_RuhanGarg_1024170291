import java.util.Optional;

public class Q2 {
    public static void main(String[] args) {

        Optional<String> email = Optional.of("ruhan.garg@thapar.edu");

        Optional<String> emptyEmail = Optional.empty();

        System.out.print("Email (ifPresent): ");
        email.ifPresent(System.out::println);
        emptyEmail.ifPresent(e -> System.out.println("This will not print"));

        String result = emptyEmail.orElse("default@thapar.edu");
        System.out.println("Email (orElse): " + result);

        try {
            String value = emptyEmail.orElseThrow(
                () -> new IllegalArgumentException("Email address is missing!"));
            System.out.println(value);
        } catch (IllegalArgumentException ex) {
            System.out.println("Exception caught: " + ex.getMessage());
        }
    }
}

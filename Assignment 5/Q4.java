import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter event name: ");
        String name = sc.nextLine();

        System.out.print("Enter event date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        System.out.print("Enter start time (HH:mm): ");
        LocalTime start = LocalTime.parse(sc.nextLine());

        System.out.print("Enter venue (press Enter to skip): ");
        String v = sc.nextLine().trim();
        Optional<String> venue = v.isEmpty() ? Optional.empty() : Optional.of(v);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
        System.out.println("\n--- Event Details ---");
        System.out.println("Event : " + name);
        System.out.println("Date  : " + date.format(df));
        System.out.println("Time  : " + start.format(tf));

        System.out.println("Venue : " + venue.orElse("Venue not specified"));

        LocalDate today = LocalDate.now();
        if (date.isAfter(today)) {
            System.out.println("Status: Event is in the future");
        } else if (date.isEqual(today)) {
            System.out.println("Status: Event is today");
        } else {
            System.out.println("Status: Event is in the past");
        }

        long days = ChronoUnit.DAYS.between(today, date);
        if (days > 0) {
            System.out.println("Days remaining: " + days);
        } else {
            System.out.println("Days remaining: 0 (event is not upcoming)");
        }

        LocalTime end = start.plusHours(2);
        System.out.println("End time (2 hrs): " + end.format(tf));

        sc.close();
    }
}

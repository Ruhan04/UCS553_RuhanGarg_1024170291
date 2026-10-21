import java.util.Objects;
import java.util.Optional;

class GenericUtil {

    public static <T> boolean isEqual(T a, T b) {
        return Objects.equals(a, b);
    }

    public static <T> T getDefault(Optional<T> value, T defaultValue) {
        return value.orElse(defaultValue);
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }
}

public class Q5 {
    public static void main(String[] args) {

        System.out.println("isEqual(\"java\", \"java\") : " + GenericUtil.isEqual("java", "java"));
        System.out.println("isEqual(10, 20)           : " + GenericUtil.isEqual(10, 20));
        System.out.println("isEqual(2.5, 2.5)         : " + GenericUtil.isEqual(2.5, 2.5));

        Optional<String> present = Optional.of("Hello");
        Optional<String> absent = Optional.empty();
        System.out.println("getDefault(present)       : " + GenericUtil.getDefault(present, "Default"));
        System.out.println("getDefault(absent)        : " + GenericUtil.getDefault(absent, "Default"));
        System.out.println("getDefault(Optional<Integer>) : "
                + GenericUtil.getDefault(Optional.<Integer>empty(), 99));

        System.out.println("max(15, 27)               : " + GenericUtil.max(15, 27));
        System.out.println("max(\"apple\", \"mango\")     : " + GenericUtil.max("apple", "mango"));
        System.out.println("max(3.14, 2.71)           : " + GenericUtil.max(3.14, 2.71));
        System.out.println("max('a', 'z')             : " + GenericUtil.max('a', 'z'));

        String s = null;
        System.out.println("isNull(null)              : " + GenericUtil.isNull(s));
        System.out.println("isNull(\"text\")            : " + GenericUtil.isNull("text"));
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        int[] numbers = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < numbers.length; i++) {
            if (binarySearch(numbers, i) == i) {
                System.out.println(true);
                return;
            }
        }

        System.out.println(false);
    }

    public static int binarySearch(int[] numbers, int value) {
        return binarySearch(numbers, value, 0, numbers.length);
    }

    private static int binarySearch(int[] numbers, int value, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) >>> 1;

        if (numbers[mid] == value) {
            return mid;
        } else if (value < numbers[mid]) {
            return binarySearch(numbers, value, left, mid - 1);
        } else {
            return binarySearch(numbers, value, mid + 1, right);
        }
    }
}
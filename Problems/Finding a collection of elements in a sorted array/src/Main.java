import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        int[] numbers = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        scanner.nextLine();
        int[] toFind = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int[] results = new int[toFind.length];

        for (int i = 0; i < toFind.length; i++) {
            results[i] = binarySearch(toFind[i], numbers);
        }

        System.out.println(Arrays.toString(results).replaceAll("[\\[\\],]", ""));
    }

    public static int binarySearch(int value, int[] numbers) {
        return binarySearch(numbers, value, 0, numbers.length - 1);
    }

    private static int binarySearch(int[] numbers, int value, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) >>> 1;

        if (numbers[mid] == value) {
            return mid + 1;
        } else if (value < numbers[mid]) {
            return binarySearch(numbers, value, left, mid - 1);
        } else {
            return binarySearch(numbers, value, mid + 1, right);
        }
    }
}
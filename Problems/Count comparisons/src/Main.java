import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        int[] numbers = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        System.out.println(findComparisons(numbers, scanner.nextInt()));
    }

    private static int findComparisons(int[] numbers, int value) {
        int count = 0;
        int currentRight = 0;
        int prevRight = 0;

        if (numbers.length == 0) {
            return 0;
        }

        if (numbers[currentRight] == value) {
            return 1;
        }

        int jumpLength = (int) Math.sqrt(numbers.length);

        while (currentRight < numbers.length - 1) {
            currentRight = Math.min(numbers.length - 1, currentRight + jumpLength);

            count++;
            if (numbers[currentRight] >= value) {
                break;
            }

            prevRight = currentRight;
        }

        if ((currentRight == numbers.length - 1) && numbers[currentRight] < value) {
            return count + 1;
        }

        return count + backwardSearch(numbers, value, prevRight, currentRight);
    }

    private static int backwardSearch(int[] numbers, int value, int prevRight, int currentRight) {
        int count = 0;
        for (int i = currentRight; i > prevRight; i--) {
            count++;
            if (numbers[i] <= value) {
                break;
            }
        }
        return count;
    }
}
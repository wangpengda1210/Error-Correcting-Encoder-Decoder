import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        int[] numbers = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        findBlock(numbers, scanner.nextInt());
    }

    private static void findBlock(int[] numbers, int value) {
        int currentRight = -1;
        int prevRight = -1;

        if (numbers.length == 0) {
            System.out.println(-1);
            return;
        }

        int jumpLength = (int) Math.sqrt(numbers.length);

        while (currentRight < numbers.length - 1) {
            currentRight = Math.min(numbers.length - 1, currentRight + jumpLength);

            if (numbers[currentRight] >= value) {
                break;
            }

            prevRight = currentRight;
        }

        if (currentRight == numbers.length - 1 && value > numbers[currentRight]) {
            System.out.println(-1);
        } else {
            System.out.println(prevRight + 1 + " " + currentRight);
        }
    }
}
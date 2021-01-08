import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /* Modify this method */
    public static int binarySearch(int value, int[] numbers) {
        return binarySearch(numbers, value, 0, numbers.length);
    }

    private static int binarySearch(int[] numbers, int value, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) >>> 1;

        if (numbers[mid] == value) {
            while (mid >= 1 && numbers[mid - 1] == value) {
                mid--;
            }
            return mid;
        } else if (value > numbers[mid]) {
            return binarySearch(numbers, value, left, mid - 1);
        } else {
            return binarySearch(numbers, value, mid + 1, right);
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int elem = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        System.out.println(binarySearch(elem, array));
    }
}
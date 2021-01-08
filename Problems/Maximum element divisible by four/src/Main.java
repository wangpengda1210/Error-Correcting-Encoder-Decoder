import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int count = scanner.nextInt();

        int max = 0;
        for (int i = 0; i < count; i++) {
            int next = scanner.nextInt();
            if (next % 4 == 0 && next > max) {
                max = next;
            }
        }

        System.out.println(max);
    }
}
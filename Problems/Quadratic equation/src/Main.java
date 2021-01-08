import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        double delta = b * b - 4 * a * c;
        if (delta > 0) {
            double first = (-b + Math.sqrt(delta)) / (2 * a);
            double second = (-b - Math.sqrt(delta)) / (2 * a);
            System.out.print(Math.min(first, second) + " " + Math.max(first, second));
        }
    }
}
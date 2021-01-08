package correcter;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Stage1Main {

    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789" +
            ".,/?;:[]{}|+=-_!@#$%^&*()" +
            "我你他好坏人地天金木水火土甲乙丙丁" +
            "あいうえおかきくけこまみむめもぱぴぷぺぽ";

    private static final Random random = new Random();

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        System.out.println(input);
        String tripleInput = tripleText(input);
        System.out.println(tripleInput);
        String errorText = generateError(tripleInput);
        System.out.println(errorText);
        String correctedText = correctError(errorText);
        System.out.println(correctedText);
    }

    private static String generateError(String input) {
        int blockStart = 0;

        while (blockStart < input.length()) {
            int indexToChange = Math.min(input.length() - 1,
                    blockStart + random.nextInt(3));

            char newSymbol = SYMBOLS.charAt(random.nextInt(SYMBOLS.length()));
            while (newSymbol == input.charAt(indexToChange)) {
                newSymbol = SYMBOLS.charAt(random.nextInt(SYMBOLS.length()));
            }

            char[] chars = input.toCharArray();
            chars[indexToChange] = newSymbol;

            input = new String(chars);

            blockStart += 3;
        }

        return input;
    }

    private static String tripleText(String text) {
        StringBuilder sb = new StringBuilder();

        for (char c : text.toCharArray()) {
            sb.append(String.valueOf(c).repeat(3));
        }

        return sb.toString();
    }

    private static String correctError(String errorText) {
        int blockStart = 0;

        assert errorText.length() % 3 == 0;

        StringBuilder sb = new StringBuilder();

        while (blockStart < errorText.length()) {
            String currentSymbol = errorText.substring(blockStart, blockStart + 3);
            char firstChar = currentSymbol.charAt(0);
            char secondChar = currentSymbol.charAt(1);
            char thirdChar = currentSymbol.charAt(2);

            if (firstChar == secondChar || firstChar == thirdChar) {
                sb.append(currentSymbol.charAt(0));
            } else if (secondChar == thirdChar) {
                sb.append(currentSymbol.charAt(1));
            } else {
                throw new InputMismatchException("Error");
            }

            blockStart += 3;
        }

        return sb.toString();
    }

}

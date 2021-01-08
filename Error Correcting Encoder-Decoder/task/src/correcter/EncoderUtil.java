package correcter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EncoderUtil {

    private static final Random random = new Random();

    public static byte[] encodeString(byte[] input) {
        ArrayList<Byte> encoded = new ArrayList<>();
        StringBuilder currentByte = new StringBuilder();

        for (byte b : input) {
            String binString = byteToBinString(b);

            for (char c : binString.toCharArray()) {
                currentByte.append(c).append(c);

                if (currentByte.length() == 6) {
                    int parity = getParity(currentByte);
                    currentByte.append(parity).append(parity);

                    encoded.add(valueOfBinByte(currentByte.toString()));
                    currentByte = new StringBuilder();
                }
            }
        }

        if (currentByte.length() > 0) {
            while (currentByte.length() < 6) {
                currentByte.append(0).append(0);
            }

            int lastParity = getParity(currentByte);
            currentByte.append(lastParity).append(lastParity);
            encoded.add(valueOfBinByte(currentByte.toString()));
        }

        byte[] result = new byte[encoded.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = encoded.get(i);
        }

        return result;
    }

    private static String byteToBinString(byte b) {
        String byteString = Integer.toBinaryString(b);
        if (byteString.length() > 8) {
            byteString = byteString.substring(byteString.length() - 8);
        } else if (byteString.length() < 8) {
            byteString = "0".repeat(8 - byteString.length()) + byteString;
        }
        return byteString;
    }

    private static int getParity(StringBuilder currentByte) {
        return (Integer.parseInt(currentByte.charAt(0) + "") +
                Integer.parseInt(currentByte.charAt(2) + "") +
                Integer.parseInt(currentByte.charAt(4) + "")) % 2;
    }

    private static byte valueOfBinByte(String s) {
        int a = Integer.valueOf(s, 2);
        if (a > 255) {
            throw new NumberFormatException("Value out of range.");
        }

        a = -((a ^ 0b11111111) + 1);

        return (byte) a;
    }

    public static byte[] decodeString(byte[] received) {
        StringBuilder decodedBytes = new StringBuilder();

        for (byte b : received) {
            String errorByte = byteToBinString(b);

            if (errorByte.charAt(6) != errorByte.charAt(7)) {
                decodedBytes.append(errorByte.charAt(0))
                        .append(errorByte.charAt(2))
                        .append(errorByte.charAt(4));
            } else {
                int parity = Integer.parseInt(errorByte.charAt(6) + "");
                int first = Integer.parseInt(errorByte.charAt(0) + "");
                int second = Integer.parseInt(errorByte.charAt(2) + "");
                int third = Integer.parseInt(errorByte.charAt(4) + "");
                if (errorByte.charAt(0) != errorByte.charAt(1)) {
                    if ((second + third) % 2 == parity) {
                        decodedBytes.append(0);
                    } else {
                        decodedBytes.append(1);
                    }
                    decodedBytes.append(second).append(third);
                } else if (errorByte.charAt(2) != errorByte.charAt(3)) {
                    decodedBytes.append(first);
                    if ((first + third) % 2 == parity) {
                        decodedBytes.append(0);
                    } else {
                        decodedBytes.append(1);
                    }
                    decodedBytes.append(third);
                } else {
                    decodedBytes.append(first).append(second);
                    if ((first + second) % 2 == parity) {
                        decodedBytes.append(0);
                    } else {
                        decodedBytes.append(1);
                    }
                }
            }

        }

        String decoded = decodedBytes.toString();
        byte[] result = new byte[decoded.length() / 8];
        int currentStart = 0;
        while (currentStart + 8 <= decoded.length()) {
            result[currentStart / 8] = valueOfBinByte(decoded
                    .substring(currentStart, currentStart + 8));
            currentStart += 8;
        }

        return result;
    }

    public static byte[] generateBitError(byte[] encodedInput) {
        byte[] errorInput = new byte[encodedInput.length];

        int currentPosition = 0;
        for (byte b : encodedInput) {

            int positionToShift = random.nextInt(8);
            b ^= 1 << positionToShift;

            errorInput[currentPosition] = b;

            currentPosition++;
        }

        return errorInput;
    }

    public static byte[] hammingEncode(byte[] input) {
        byte[] encodedInput = new byte[input.length * 2];

        int currentPosition = 0;
        for (byte b : input) {
            String byteString = byteToBinString(b);

            String resultByteFirst = "00" + byteString.charAt(0) + "0" +
                    byteString.charAt(1) + byteString.charAt(2) + byteString.charAt(3) +
                    "0";
            encodedInput[currentPosition] = valueOfBinByte(getHamming(resultByteFirst));


            String resultByteSecond = "00" + byteString.charAt(4) + "0" +
                    byteString.charAt(5) + byteString.charAt(6) + byteString.charAt(7) +
                    "0";
            encodedInput[currentPosition + 1] = valueOfBinByte(getHamming(resultByteSecond));

            currentPosition += 2;
        }

        return encodedInput;
    }

    private static String getHamming(String string) {
        int firstParity = string.charAt(0) ^ string.charAt(2) ^
                string.charAt(4) ^ string.charAt(6);
        int secondParity = string.charAt(1) ^ string.charAt(2) ^
                string.charAt(5) ^ string.charAt(6);
        int thirdParity = string.charAt(3) ^ string.charAt(4) ^
                string.charAt(5) ^ string.charAt(6);

        string = replaceIndex(string, 0, String.valueOf(firstParity).charAt(0));
        string = replaceIndex(string, 1, String.valueOf(secondParity).charAt(0));
        return replaceIndex(string, 3, String.valueOf(thirdParity).charAt(0));
    }

    private static String replaceIndex(String string, int index, char replaceWith) {
        char[] chars = string.toCharArray();
        chars[index] = replaceWith;
        return Arrays.toString(chars).replaceAll("[\\[\\], ]", "");
    }

    public static byte[] hammingDecode(byte[] received) {
        byte[] result = new byte[received.length / 2];

        int currentPosition = 0;
        StringBuilder currentByte = new StringBuilder();
        for (byte b : received) {
            String errorByte = byteToBinString(b);
            int errorPosition = Integer.parseInt((errorByte.charAt(3) ^
                    errorByte.charAt(4) ^ errorByte.charAt(5) ^ errorByte.charAt(6)) +
                    String.valueOf(errorByte.charAt(1) ^ errorByte.charAt(2) ^
                            errorByte.charAt(5) ^ errorByte.charAt(6)) +
                    (errorByte.charAt(0) ^ errorByte.charAt(2) ^
                            errorByte.charAt(4) ^ errorByte.charAt(6)), 2);

            if (errorPosition == 0) {
                errorPosition = 7;
            } else {
                errorPosition--;
            }

            if (errorByte.charAt(errorPosition) == '0') {
                errorByte = replaceIndex(errorByte, errorPosition, '1');
            } else {
                errorByte = replaceIndex(errorByte, errorPosition, '0');
            }

            currentByte.append(errorByte.charAt(2)).append(errorByte.charAt(4))
                    .append(errorByte.charAt(5)).append(errorByte.charAt(6));

            if (currentByte.length() >= 8) {
                result[currentPosition] = valueOfBinByte(currentByte.toString());
                currentPosition++;
                currentByte = new StringBuilder();
            }
        }

        return result;
    }

}

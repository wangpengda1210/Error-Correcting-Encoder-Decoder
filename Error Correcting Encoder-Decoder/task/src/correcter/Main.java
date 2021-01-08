package correcter;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Write a mode: ");
        switch (new Scanner(System.in).next()) {
            case "encode":
                try {
                    byte[] input = IOUtil.readFile("send.txt");
                    IOUtil.writeFile("encoded.txt", EncoderUtil.hammingEncode(input));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "send":
                try {
                    byte[] encodedInput = IOUtil.readFile("encoded.txt");
                    IOUtil.writeFile("received.txt", EncoderUtil.generateBitError(encodedInput));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "decode":
                try {
                    byte[] received = IOUtil.readFile("received.txt");
                    IOUtil.writeFile("decoded.txt", EncoderUtil.hammingDecode(received));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Not a valid choice");
                break;
        }
    }

}

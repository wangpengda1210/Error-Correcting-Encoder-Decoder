package correcter;

import java.io.*;

public class IOUtil {

    public static byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);

        byte[] result = fis.readAllBytes();

        fis.close();
        return result;
    }

    public static void writeFile(String fileName, byte[] content) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);

        fos.write(content);
        fos.close();
    }

}

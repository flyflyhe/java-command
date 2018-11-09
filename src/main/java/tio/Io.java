package tio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class Io {
    public static void nioReadFile(String filename) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);

        while (true) {
            byteBuffer.clear();
            int r = fileChannel.read(byteBuffer);

            if (r == -1) {
                break;
            }

            byteBuffer.flip();
            byte[] bytes = byteBuffer.array();
            if (byteBuffer.limit() != byteBuffer.capacity()) { //如果数组不满
                bytes = Arrays.copyOfRange(bytes, 0, byteBuffer.limit());
            }
            byteArrayOutputStream.write(bytes);
        }

        System.out.println(new String(byteArrayOutputStream.toByteArray()));
    }
}

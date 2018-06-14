package com.waes.diff;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class TestUtils {

    public static String getTestNode(String fileName) throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(classLoader.getResourceAsStream(fileName), "UTF-8");
        while(true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0) {
                break;
            }
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

}

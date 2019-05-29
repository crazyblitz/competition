package com.ley.innovation.contest.typehandler;


import java.io.Closeable;
import java.io.IOException;

/**
 * close stream if stream instanceof {@link Closeable}
 **/
public class CloseUtils {

    /**
     * close streams
     **/
    public static void close(Closeable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogUtils {

    private LogUtils() {
        // Utility class
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
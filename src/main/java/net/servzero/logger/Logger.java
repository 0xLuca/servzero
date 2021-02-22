package net.servzero.logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.SimpleLayout;

public class Logger {
    private static final org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getRootLogger();
        logger.addAppender(new ConsoleAppender(new SimpleLayout()));
        logger.setLevel(Level.INFO);
    }

    public static org.apache.log4j.Logger getLogger() {
        return logger;
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }
}

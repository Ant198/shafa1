package com.demo.core.allure;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class AllureLogger {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private LogType logType;
    private final java.util.logging.Logger log = java.util.logging.Logger.getLogger(this.getClass().getName());

    @Step("{message}")
    private void log(String message) {
        switch (logType) {
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default:
                logger.info(message);
        }
    }

    protected void logInfo(String message, Object... args) {
        logType = LogType.INFO;
        String escapedMessage = message.replaceAll("%", "%%");
        log(String.format(escapedMessage, args));
    }

    protected void logWarn(String message, Object... args) {
        logType = LogType.WARN;
        log(String.format(message, args));
    }

    protected void logError(String message, Object... args) {
        logType = LogType.ERROR;
        log(String.format(message, args));
    }

    private enum LogType {
        INFO, WARN, ERROR
    }

    protected void logInFile(String message, Object... args) {
        if (System.getProperty("inFile", "false").equals("true")) {
            log.log(Level.INFO, message);
        }
    }

    protected void configLog(String testName) {
        if (System.getProperty("inFile", "false").equals("true")) {
            FileHandler fh = null;   // true forces append mode
            try {
                File file = new File("Files/Logs");
                if (!file.exists()) {
                    file.mkdir();
                }
                fh = new FileHandler("Files/Logs/" + testName + ".txt", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SimpleFormatter sf = new SimpleFormatter();

            fh.setFormatter(sf);
            log.addHandler(fh);
        }
    }
}

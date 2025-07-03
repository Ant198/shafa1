package com.demo.core.allure;

import io.qameta.allure.Attachment;
import org.apache.commons.io.IOUtils;

import java.io.*;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;
import static com.google.common.io.Files.toByteArray;

public class AllureTools {
    private static File newFile;
    private static InputStream inputStream;

    @SuppressWarnings({"UnusedReturnValue", "UnstableApiUsage"})
    @Attachment(value = "Page Screenshot", type = "image/png")
    public static byte[] attachScreenshot() {
        try {
            return toByteArray(takeScreenShotAsFile());
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @SuppressWarnings({"UnusedReturnValue", "ResultOfMethodCallIgnored"})
    @Attachment(value = "Log File", type = "text/plain")
    public static byte[] attachLogFile() {
        File folder = new File("Files");
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".log")) {
                newFile = file;
            }
        }

        try {
            inputStream = new FileInputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            newFile.delete();
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

package com.demo.utils;

import java.io.File;

public class FileUploader {

    public static void uploadFileToJenkins() {
        // Получаем путь к файлу из переменной окружения
        String filePathStr = System.getenv("UPLOAD_FILE");

        if (filePathStr == null) {
            System.err.println("Переменная окружения UPLOAD_FILE не установлена!");
            return;
        }

        File uploadedFile = new File(filePathStr);

        if (!uploadedFile.exists()) {
            System.err.println("Файл не найден: " + uploadedFile.getAbsolutePath());
            return;
        }

        System.out.println("Файл найден: " + uploadedFile.getAbsolutePath());

        // Здесь можно дальше работать с файлом
    }
}

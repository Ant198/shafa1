package com.demo.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;


public class PropertyLoader {

    public static String get(String key) {
        return Objects.requireNonNull(getProperties()).getProperty(key);
    }

    private static Properties getProperties() {
        try {
            FileInputStream in = new FileInputStream("src/main/resources/environments.properties");
            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            Properties toSystem = new Properties();
            toSystem.load(inputStreamReader);
            return toSystem;
        } catch (FileNotFoundException e) {
            //ignore
        } catch (IOException e) {
            System.exit(1);
        }

        return null;
    }
}

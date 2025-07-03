package com.demo.utils;

import java.util.HashMap;

public class EnvironmentVariablesProvider {

    private static HashMap<String, Object> environment = new HashMap<>();

    public static void getEnvironment(String environmentName, String environmentsContentRootLocation) throws Exception {
        String jsonLocation = System.getProperty("user.dir") + "/" + environmentsContentRootLocation;
        environment = ParseJSON.convertJSONObjectToHashMap(environmentName, jsonLocation);
    }

    public static String getUrl() {
        return environment.get("webUrl").toString();
    }

    public static String getEmailAdmin() {
        return environment.get("emailAdmin").toString();
    }

    public static String getEmailPm() {
        return environment.get("emailPm").toString();
    }

    public static String getPassword() {
        return environment.get("password").toString();
    }

    public static String getPropertyName() {
        return environment.get("property").toString();
    }

    public static String getPropertyWithSGT() {
        return environment.get("propertyWithSGT").toString();
    }

    public static String getWebFormUrl() {
        return environment.get("webFromUrl").toString();
    }


}

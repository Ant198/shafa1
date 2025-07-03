package com.demo;

import org.testng.TestNG;

import java.util.List;

public class TestLauncherGUI {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestSuites(List.of("src/test/resources/AllTest.xml"));
        testng.run();
    }
}

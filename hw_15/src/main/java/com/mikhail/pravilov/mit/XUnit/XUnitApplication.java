package com.mikhail.pravilov.mit.XUnit;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class that implements console client of XUnit test system.
 */
public class XUnitApplication {
    private static void printUsage() {
        System.err.println("Arguments: <path to tests root directory (without package dirs)> " +
                "<fully qualified name of class with package (without .class extension)>");
    }

    public static void main(@NotNull String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        Class testClass;
        try {
            testClass = getTestingClass(Paths.get(args[0]), args[1]);
        } catch (MalformedURLException e) {
            System.err.println("Cannot get path to directory in URL format. Error: " + e.getLocalizedMessage());
            printUsage();
            return;
        } catch (ClassNotFoundException e) {
            System.err.println("Given class not found. Error: " + e.getLocalizedMessage());
            printUsage();
            return;
        }

        XUnit xUnit;
        xUnit = new XUnit(testClass);
        List<TestResult> testResults = xUnit.runTests();
        int numberOfFailed = 0;
        PrintStream out = System.out;
        for (TestResult testResult : testResults) {
            out.println("<TEST STARTS ==============================================>");
            if (testResult.isIgnored()) {
                out.println("Test " + testResult.getMethodName() + " is ignored");
                out.println("Reason: " + testResult.getIgnoreReason());
            }
            else {
                out.println("Test for " + testResult.getMethodName());
                out.println("Result: " + (testResult.isPassed() ? "OK" : "FAILED"));
                out.println("Time: " + TimeUnit.NANOSECONDS.toSeconds(testResult.getTime()));
                if (!testResult.isPassed()) {
                    out.println("Fail reason: " + testResult.getFailReason());
                    if (testResult.getException() != null) {
                        out.println("Thrown Exception: " + testResult.getException().toString());
                        out.println("Exception message: " + testResult.getException().getLocalizedMessage());
                        out.println("Stack trace: ");
                        testResult.getException().printStackTrace();
                    }
                    numberOfFailed++;
                }
            }
            out.println("<TEST ENDS ================================================>");
        }
        out.println("Number of fails: " + numberOfFailed);
    }

    @NotNull
    private static Class getTestingClass(@NotNull Path pathToTestDirectory, @NotNull String classQualifiedName)
            throws MalformedURLException, ClassNotFoundException {
        return (new URLClassLoader(new URL[] {pathToTestDirectory.toUri().toURL()})).loadClass(classQualifiedName);
    }


}

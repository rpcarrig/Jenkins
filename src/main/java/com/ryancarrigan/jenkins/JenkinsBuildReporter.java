package com.ryancarrigan.jenkins;

import com.ryancarrigan.jenkins.build.Build;
import com.ryancarrigan.jenkins.build.data.*;
import com.ryancarrigan.jenkins.build.data.status.TestFailCategory;
import com.ryancarrigan.jenkins.build.data.status.TestStatus;
import com.ryancarrigan.jenkins.build.xml.BuildParser;

import java.util.*;

/**
 * com.ryancarrigan.jenkins.build
 *
 * @author Ryan P. Carrigan
 * @since 5/21/14.
 */
public class JenkinsBuildReporter {
    private Boolean areSkipsPresent = false;
    private Build build;
    private ChangeSet changeSet;
    private String url;

    public JenkinsBuildReporter(final String inputFileName, final String url) {
        BuildParser buildParser = new BuildParser(inputFileName);
        this.build = new Build(
            buildParser.getBuildSummary(),
            buildParser.getCause(),
            buildParser.getTestSuites(),
            url
        );
        this.changeSet = buildParser.getChangeSet();
        this.url = url;
    }

    public Set<String> getUniqueFails() {
        return getUniqueFails(build.getTestSuites());
    }

    public static Set<String> getUniqueFails(final List<TestSuite> testSuites) {
        final List<String> allTestFailStatus = new ArrayList<String>();
        for (final TestSuite testSuite : testSuites) {
            for (final TestCase test : testSuite.getTests()) {
                if (!test.wasSuccess()) {
                    if (null == test.getErrorDetails())
                        allTestFailStatus.add(test.getErrorStackTrace());
                    else
                        allTestFailStatus.add(test.getErrorDetails());
                }
            }
        }
        return new HashSet<String>(allTestFailStatus);
    }

    public TestFailCategory getUniqueFailCategory(final String failMessage) {
        if (failMessage.matches(".*ELEMENT NOT FOUND. TEST FAILED.*"))
            return TestFailCategory.ELEMENT_DEFINITION;
        if (failMessage.matches(".*Could not find property value for key.*"))
            return TestFailCategory.ELEMENT_UNDEFINED;
        if (failMessage.matches(".*Expected to be (on|at) the.*"))
            return TestFailCategory.NAVIGATION_FAILURE;
        if (failMessage.matches("(.*Expected <.+> to be online expected:<.+> actual:<.+>).*"))
            return TestFailCategory.PRODUCT_DATA;
        if (failMessage.matches("(.*(Expected .+ )?expected:<.+> actual:<.+>).*"))
            return TestFailCategory.ASSERTION;
        if (failMessage.matches(".*(Driver info:)|(java.lang.NullPointerException).*"))
            return TestFailCategory.NPE;
        if (failMessage.matches(".*Element is not currently visible and so may not be interacted.*"))
            return TestFailCategory.ELEMENT_HIDDEN;
        if (failMessage.matches(".*Element not found in the cache.*"))
            return TestFailCategory.ELEMENT_STALE;
        if (failMessage.matches(".*Error creating bean with name.*"))
            return TestFailCategory.SPRING;
        if (failMessage.matches(".*Element is disabled and.*"))
            return TestFailCategory.ELEMENT_DISABLED;
        if (failMessage.matches(".*Error communicating with the.*"))
            return TestFailCategory.REMOTE_BROWSER;
        if (failMessage.matches(".*Element is.*"))
            return TestFailCategory.WEB_DRIVER_EXCEPTION;
        return TestFailCategory.UNCLASSIFIED;
    }

    public HashMap<String, Integer> getUniqueFailsAndCount() {
        final HashMap<String, Integer> uniqueFailsAndCount = new HashMap<String, Integer>();
        for (final String uniqueFail : getUniqueFails()) {
            Integer failCount = 0;
            for (final TestSuite suite : build.getTestSuites()) {
                for (final TestCase test : suite.getTests()) {
                    if (!test.wasSuccess()) {
                        if (test.getErrorDetails().equalsIgnoreCase(uniqueFail))
                            ++failCount;
                    } else if (test.getStatus().equals(TestStatus.SKIPPED))
                        areSkipsPresent = true;
                }
            }
            uniqueFailsAndCount.put(uniqueFail, failCount);
        }
        return uniqueFailsAndCount;
    }

    public HashMap<TestFailCategory, Integer> getFailCategoriesAndCount() {
        final HashMap<TestFailCategory, Integer> failCategoriesAndCount = new HashMap<TestFailCategory, Integer>();
        final HashMap<String, Integer> uniqueFailsAndCount = getUniqueFailsAndCount();
        for (final String uniqueFail : uniqueFailsAndCount.keySet()) {
            final TestFailCategory failCategory = getUniqueFailCategory(uniqueFail);

            Integer failCategoryTotal = failCategoriesAndCount.get(failCategory);
            if (null != failCategoryTotal)
                failCategoryTotal += uniqueFailsAndCount.get(uniqueFail);
            else
                failCategoryTotal = 0;
            failCategoriesAndCount.put(failCategory, failCategoryTotal);
        }
        return failCategoriesAndCount;
    }

    public HashMap<String, ArrayList<String>> getTestsAffectedByUniqueFails() {
        final HashMap<String, ArrayList<String>> testsAffectedByUniqueFails = new HashMap<String, ArrayList<String>>();
        for (final String uniqueFail : getUniqueFails()) {
            testsAffectedByUniqueFails.put(uniqueFail, new ArrayList<String>());
            for (final TestSuite testSuite : build.getTestSuites()) {
                for (final TestCase test : testSuite.getTests()) {
                    if (null != test.getStatus()) {
                        if (!test.wasSuccess() && test.getErrorDetails().equals(uniqueFail)) {
                            final ArrayList<String> testsAffected = testsAffectedByUniqueFails.get(uniqueFail);
                            testsAffected.add(test.getSimpleClassAndTestName());
                            testsAffectedByUniqueFails.put(uniqueFail, testsAffected);
                        }
                    } else if (test.getStatus().equals(TestStatus.SKIPPED))
                        areSkipsPresent = true;
                }
            }
        }
        return testsAffectedByUniqueFails;
    }

    public TestStatus getTestStatusByName(final String testName) {
        for (final TestSuite testSuite : build.getTestSuites()) {
            for (final TestCase test : testSuite.getTests()) {
                if (null != test && test.getTestName().contains(testName))
                    return test.getStatus();
            }
        }
        return TestStatus.NOT_FOUND;
    }
}

package testClasses;

import com.mikhail.pravilov.mit.XUnit.annotations.*;

public class ComplexCaseTestClass {
    private static int testNumber;
    private int beforeNumber;

    @BeforeClass
    public static void beforeClassMethod() {
        testNumber = 200;
    }

    @Before
    public void beforeMethod() {
        beforeNumber = 10;
    }

    @Test
    public void testBefore() {
        assert beforeNumber == 10;
        beforeNumber++;
    }

    @Test
    public void testBeforeRunsEachTime() {
        assert beforeNumber == 10;
    }

    // Must Fail
    @Test
    public void failTest() {
        throw new AssertionError();
    }

    @Test
    public void successTest() {
        assert testNumber == 200;
    }

    @Test(ignore = "This method is ignored")
    public void ignoredTest() {
        assert false;
    }

    @Test(expected = Exception.class)
    public void exceptionIsThrown() throws Exception {
        throw new Exception();
    }

    // Must Fail
    @Test(expected = Exception.class)
    public void exceptionExpectedButNotThrown() throws Exception {
    }

    @After
    public void afterTest() {
        System.out.println("After method runs");
    }

    @AfterClass
    public static void afterClassTest() {
        System.out.println("AfterClass method runs");
    }
}

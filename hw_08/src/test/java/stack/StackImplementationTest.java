package stack;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackImplementationTest {
    private StackImplementation<Double> testStack = new StackImplementation<>();

    @Test
    public void push() throws Exception {
        assertEquals(new Double(20), testStack.push(20d));
        assertEquals(new Double(20), testStack.peek());
        assertEquals(new Double(3), testStack.push(3d));
        assertEquals(new Double(3), testStack.peek());
        assertEquals(new Double(10), testStack.push(10d));
        assertEquals(new Double(10), testStack.peek());
        assertEquals(new Double(20), testStack.push(20d));
        assertEquals(new Double(20), testStack.peek());
    }

    @Test
    public void pop() throws Exception {
        testStack.push(20d);
        testStack.push(3d);
        testStack.push(10d);
        testStack.push(20d);
        assertEquals(new Double(20), testStack.pop());
        assertEquals(new Double(10), testStack.pop());
        testStack.push(0d);
        assertEquals(new Double(0), testStack.pop());
        assertEquals(new Double(3), testStack.pop());
        assertEquals(new Double(20), testStack.pop());
    }

    @Test
    public void empty() throws Exception {
        assertTrue(testStack.empty());
        testStack.push(2d);
        assertFalse(testStack.empty());
        testStack.pop();
        assertTrue(testStack.empty());
    }

    @Test
    public void clear() throws Exception {
        testStack.clear();
        assertTrue(testStack.empty());
        testStack.push(2d);
        testStack.clear();
        assertTrue(testStack.empty());
    }

}
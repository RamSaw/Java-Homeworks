package calculator;

import org.junit.Test;
import org.mockito.InOrder;
import stack.StackImplementation;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    @Test
    @SuppressWarnings("unchecked")
    public void calculateExpression() throws Exception {
        StackImplementation<Double> stackMock = mock(StackImplementation.class);
        Calculator calculator = new Calculator(stackMock);

        String[] testRPN = {"2", "3", "4", "2", "/", "+", "*"};
        when(stackMock.pop()).thenReturn(2d, 4d, 2d, 3d, 5d, 2d, 10d);
        assertEquals(new Double(10), calculator.calculateExpression(testRPN));
        verify(stackMock, times(7)).pop();
        InOrder inOrder = inOrder(stackMock);
        inOrder.verify(stackMock).push(2d);
        inOrder.verify(stackMock).push(3d);
        inOrder.verify(stackMock).push(4d);
        inOrder.verify(stackMock).push(2d);
        inOrder.verify(stackMock).push(2d);
        inOrder.verify(stackMock).push(5d);
        inOrder.verify(stackMock).push(10d);

        testRPN = new String[]{"22", "3", "*"};
        when(stackMock.pop()).thenReturn(22d, 3d, 66d);
        assertEquals(new Double(66), calculator.calculateExpression(testRPN));
        verify(stackMock, times(10)).pop();
        inOrder.verify(stackMock).push(22d);
        inOrder.verify(stackMock).push(3d);
        inOrder.verify(stackMock).push(66d);
    }

    @Test(expected = NumberFormatException.class)
    public void testThrowsNumberFormatException() throws Exception {
        Calculator calculator = new Calculator(new StackImplementation<>());
        calculator.calculateExpression(new String[]{"22", "3", "%"});
    }

    @Test(expected = NumberFormatException.class)
    public void testThrowsNumberFormatExceptionNotNumber() throws Exception {
        Calculator calculator = new Calculator(new StackImplementation<>());
        calculator.calculateExpression(new String[]{"22", "3ac", "*"});
    }
}
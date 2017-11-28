package convertInfixToRPN;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertInfixToRPNTest {
    @Test
    public void convertInfixToRPN() throws Exception {
        assertArrayEquals(new String[]{"12", "2", "4", "2", "+", "*", "4", "+", "2", "/", "+"},
                ConvertInfixToRPN.convertInfixToRPN(new String[]{"12", "+", "(", "2", "*", "(", "4", "+", "2", ")", "+", "4", ")", "/", "2"}));//12+(2*(4+2)+4

        assertArrayEquals(new String[]{"1", "0", "*", "20", "+", "30", "/"},
                ConvertInfixToRPN.convertInfixToRPN(new String[]{"(", "1", "*", "0", "+", "20", ")", "/", "30"}));

        assertArrayEquals(new String[]{"1", "2", "+"},
                ConvertInfixToRPN.convertInfixToRPN(new String[]{"(", "1", "+", "2", ")"}));
    }

}
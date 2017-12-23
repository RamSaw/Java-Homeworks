import calculator.Calculator;
import convertInfixToRPN.ConvertInfixToRPN;
import stack.StackImplementation;

public class programCalculator {
    /**
     * Program that calculates expression from args.
     * @param args expression to calculate.
     */
    public static void main(String[] args) {
        String[] tokens = args[0].split(" ");
        String[] reversePolishNotation = ConvertInfixToRPN.convertInfixToRPN(tokens);
        Calculator calculator = new Calculator(new StackImplementation<>());
        Double result = calculator.calculateExpression(reversePolishNotation);
        System.out.print(result);
    }
}

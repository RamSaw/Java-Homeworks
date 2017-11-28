package calculator;

import stack.Stack;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Class to calculate expression given in postfix notation
 */
public class Calculator {
    private Stack<Double> numbers;

    /**
     * Calculator constructor.
     * @param numbers stack implementation where to store numbers.
     */
    public Calculator(Stack<Double> numbers) {
        this.numbers = numbers;
    }

    /**
     * Method to calculate expression in postfix notation.
     * @param reversePolishNotation expression in reverse polish notation.
     * @return calculated value.
     */
    public Double calculateExpression(String[] reversePolishNotation) {
        numbers.clear();

        Arrays.asList(reversePolishNotation).stream().forEach(number -> {
            switch(number) {
                case "+":
                    calculateBySign((n1, n2) -> n2 + n1);
                    break;
                case "-":
                    calculateBySign((n1, n2) -> n2 - n1);
                    break;
                case "*":
                    calculateBySign((n1, n2) -> n2 * n1);
                    break;
                case "/":
                    calculateBySign((n1, n2) -> n2 / n1);
                    break;
                default:
                    numbers.push(Double.parseDouble(number));
            }
        });
        return numbers.pop();
    }

    /**
     * Function to apply to 2 operands in expression.
     * @param operation function to apply.
     */
    private void calculateBySign(BiFunction<Double, Double, Double> operation) {
        numbers.push(operation.apply(numbers.pop(), numbers.pop()));
    }
}

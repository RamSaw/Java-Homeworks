package calculator;

import org.jetbrains.annotations.NotNull;
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
    public Calculator(@NotNull Stack<Double> numbers) {
        this.numbers = numbers;
    }

    /**
     * Method to calculate expression in postfix notation.
     * @param reversePolishNotation expression in reverse polish notation.
     * @return calculated value.
     * @throws NumberFormatException if it meets unknown expression (not +,-,*,/ or number).
     */
    @NotNull
    public Double calculateExpression(@NotNull String[] reversePolishNotation) throws NumberFormatException {
        numbers.clear();

        Arrays.stream(reversePolishNotation).forEach(number -> {
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
    private void calculateBySign(@NotNull BiFunction<Double, Double, Double> operation) {
        numbers.push(operation.apply(numbers.pop(), numbers.pop()));
    }
}

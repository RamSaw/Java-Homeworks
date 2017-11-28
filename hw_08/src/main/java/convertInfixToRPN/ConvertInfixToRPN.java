package convertInfixToRPN;


import java.util.*;

/**
 * Class to convert infix expression to postfix one.
 */
public class ConvertInfixToRPN {

    /**
     * Checks if string is number.
     * @param string to check.
     * @return true if string is number otherwise false.
     */
    private static boolean isNumber(String string) {
        try{
            Double.valueOf(string);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Converts infix expression to postfix one.
     * @param infixNotation expression in infix notation.
     * @return string array where elements are in postfix notation.
     */
    public static String[] convertInfixToRPN(String[] infixNotation) {
        Map<String, Integer> prededence = new HashMap<>();
        prededence.put("/", 5);
        prededence.put("*", 5);
        prededence.put("+", 4);
        prededence.put("-", 4);
        prededence.put("(", 0);

        LinkedList<String> queue = new LinkedList<>();
        LinkedList<String> stack = new LinkedList<>();

        for (String token : infixNotation) {
            if ("(".equals(token)) {
                stack.push(token);
                continue;
            }

            if (")".equals(token)) {
                while (!"(".equals(stack.peek())) {
                    queue.add(stack.pop());
                }
                stack.pop();
                continue;
            }
            // an operator
            if (prededence.containsKey(token)) {
                while (!stack.isEmpty() && prededence.get(token) <= prededence.get(stack.peek())) {
                    queue.add(stack.pop());
                }
                stack.push(token);
                continue;
            }

            if (isNumber(token)) {
                queue.add(token);
                continue;
            }

            throw new IllegalArgumentException("Invalid input");
        }
        // at the end, pop all the elements in stack to queue
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }

        return queue.toArray(new String[queue.size()]);
    }
}
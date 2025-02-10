package org.example;
import java.util.*;
public class Main {
    public static double
    evaluateExpression(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack to store operators
        Stack<Double> values = new Stack<>();
        // Stack to store operands
        Stack<Character> operators = new Stack<>();

        // Iterate through each character in the expression
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            // If the character is a digit or a decimal,parse the operands

            if ((tokens[i] >= '0' && tokens[i] <= '9')
                    || tokens[i] == '.') {
                StringBuilder sb = new StringBuilder();
                // Continue collecting digits including decimal point

                while (i < tokens.length
                        && (Character.isDigit(tokens[i])
                        || tokens[i] == '.')) {
                    sb.append(tokens[i]);
                    i++;
                }
                //Convert the string to double  and push to stack
                values.push(
                        Double.parseDouble(sb.toString()));
                i--;

            }
            else if (tokens[i] == '(') {

                operators.push(tokens[i]);
            }
            else if (tokens[i] == ')') {

                while (operators.peek() != '(') {
                    values.push(applyOperator(
                            operators.pop(), values.pop(),
                            values.pop()));
                }
                operators.pop();
            }
            else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '*'
                    || tokens[i] == '/') {

                while (!operators.isEmpty()
                        && hasPrecedence(tokens[i],
                        operators.peek())) {
                    values.push(applyOperator(
                            operators.pop(), values.pop(),
                            values.pop()));
                }

                operators.push(tokens[i]);
            }
        }

        // Process any remaining operators in the stack
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(),
                    values.pop(),
                    values.pop()));
        }


        return values.pop();
    }

    // Function to check if operator1 has higher precedence than 2

    private static boolean hasPrecedence(char operator1,
                                         char operator2)
    {
        if (operator2 == '(' || operator2 == ')')
            return false;
        return (operator1 != '*' && operator1 != '/')
                || (operator2 != '+' && operator2 != '-');
    }

    // Function to apply the operator to two operands
    private static double applyOperator(char operator,
                                        double b, double a)
    {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException(
                            "Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String mathematicalExpression =sc.next();
        double evaluatedResult = evaluateExpression(mathematicalExpression);
        System.out.println("Output of the expression : " + evaluatedResult);


    }
}
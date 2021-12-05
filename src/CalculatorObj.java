/**
 * Maman 13: Date 05/12/2021
 * Question 2: Calculator
 * Alexey Vartanov - 321641086
 */

/**
 * Class for Calculator object
 */
public class CalculatorObj {

    //Operators
    private static final String DELETE = "Del";
    private static final String CLEAN = "C";
    private static final String MINUS = "-";
    private static final String PLUS = "+";
    private static final String EQUAL = "=";
    private static final String POINT = ".";
    private static final String DIVIDE = "/";
    private static final String MULTIPLY = "*";
    private static final String UNARY_MINUS = "+/-";
    private static final String PERCENT = "%";

    private static final String PROMPT = "0";

    //private variables
    private boolean isSolved;
    private String usedOperator = null;
    private boolean secondNumberFlag;
    private boolean zeroException;

    private double firstNumber;
    private double secondNumber;
    private double solution;

    private String historyStr = "";
    private String outputStr = PROMPT;

    public String getOutputStr() {
        return outputStr;
    }

    public String getHistoryStr(){
        return historyStr;
    }

    /**
     * Parse Input of the calculator
     * @param input - Current data in the output field of the calculator
     * @param btnPressed - button that currently pressed as a string
     */
    public void parseInput(String input, String btnPressed) {
        if (zeroException) {                        /* if was divided by 0 before, clean the fields */
            firstNumber = 0;                        /* before to enter next date */
            outputStr = PROMPT;
            historyStr = "";
            zeroException = false;
        }
        switch (btnPressed) {
            case CLEAN:                               /* clean all fields in any cases */
                outputStr = PROMPT;
                historyStr = "";
                usedOperator = null;
                isSolved = false;
                secondNumberFlag = false;
                break;
            case DELETE:                              /* remove last entered symbol */
                if (isSolved) {                       /* if solved clean fields*/
                    historyStr = "";
                    outputStr = PROMPT;
                } else {
                    if (!input.equals(PROMPT)) {
                        outputStr = (input.substring(0, input.length() - 1));
                    }
                    if (input.length() == 1) {
                        outputStr = PROMPT;
                    }
                    if (input.length() == 2 && input.startsWith("-"))
                        outputStr = PROMPT;
                }
                break;
            case EQUAL:                         /* "=" pressed */
                try {
                    if(!isSolved){              /* solve only if not solved yet */
                        secondNumber = Double.parseDouble(input);
                        solution = calculateAnswer();
                        String format = "%f";
                        if(solution % 1 == 0)
                            format = "%.0f";
                        outputStr = String.format(format, calculateAnswer());
                        historyStr = historyStr + input + EQUAL;
                    }
                } catch (ArithmeticException exception) {
                    outputStr = exception.getMessage();
                    zeroException = true;
                }
                isSolved = true;
                usedOperator = null;
                secondNumberFlag = false;
                break;
            case PERCENT:
            case MULTIPLY:
            case DIVIDE:
            case MINUS:
            case PLUS:
                operatorCommandHandler(input, btnPressed);
                break;
            case UNARY_MINUS:                          /* if number is signed, remove, else add sign of minus */
                if (!input.equals(PROMPT)) {
                    if (input.startsWith(MINUS))
                        outputStr = input.substring(1);
                    else
                        outputStr = MINUS + input;
                }
                break;
            case POINT:
                if (!input.contains(POINT))            /* if already in the input do nothing */
                    outputStr = input + btnPressed;
                break;
            default:                                   /* numbers entered */
                if (isSolved) {                        /* if already solved clean the fields */
                    historyStr = "";
                    input = "";
                    isSolved = false;
                }                                       /* if the operator entered, it's start of second number */
                if (usedOperator != null && !secondNumberFlag) {
                    input = "";
                    secondNumberFlag = true;
                }                                       /* if first time entered, replace the prompt */
                if (input.equals(PROMPT)) {
                    input = "";
                }
                outputStr = input + btnPressed;
                break;
        }
    }

    /**
     * Calculate the final answer according to entered
     * operator
     * @return answer as double number
     */
    private double calculateAnswer() {
        if (usedOperator == null) {
            return secondNumber;
        }
        switch (usedOperator) {
            case MULTIPLY:
                return firstNumber * secondNumber;
            case DIVIDE:
                if (secondNumber == 0)
                    throw new ArithmeticException("Can't divide by zero");
                return firstNumber / secondNumber;
            case PLUS:
                return firstNumber + secondNumber;
            case MINUS:
                return firstNumber - secondNumber;
            default:
                return 0;
        }
    }

    /**
     * When operator entered, convert the previous input as to first number
     * if entered after exercise was solved, take the answer as first argument
     * @param numAsString - input before operator entered
     * @param operator - used operator
     */
    private void operatorCommandHandler(String numAsString, String operator) {
        if(isSolved){
            firstNumber = solution;
            isSolved = false;
        }
        else{
            firstNumber = Double.parseDouble(numAsString);
        }
        usedOperator = operator;
        historyStr = numAsString + operator;
    }
}
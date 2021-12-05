import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/* move calculator to different class with getter and setters */
public class CalculatorController {

    @FXML
    private TextField HistoryField;

    @FXML
    private TextField TextField;

    private boolean isSolved;
    private String usedOperator = null;
    private boolean secondNumberFlag;
    private double firstNumber = 0;
    private boolean zeroException;
    private static final String PROMPT = "0";
    //Operands
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

    @FXML
    void btnPress(ActionEvent event) {
        if (zeroException){
            firstNumber = 0;
            TextField.setText(PROMPT);
            HistoryField.setText("");
            zeroException = false;
        }
        String btnText = ((Button) event.getSource()).getText();
        String exerciseStr = TextField.getText();
        switch (btnText) {
            case CLEAN:                               /* clean all fields */
                TextField.setText(PROMPT);
                HistoryField.setText("");
                usedOperator = null;
                isSolved = false;
                secondNumberFlag = false;
                break;
            case DELETE:                             /* remove last entered symbol */
                if (isSolved){                       /* if solved clean fields*/
                    HistoryField.setText("");
                    TextField.setText(PROMPT);
                }
                else{
                    if(!exerciseStr.equals(PROMPT)){
                        TextField.setText(exerciseStr.substring(0, exerciseStr.length() - 1));
                    }
                    if(exerciseStr.length()==1){
                        TextField.setText(PROMPT);
                    }
                    if(exerciseStr.length() == 2 && exerciseStr.startsWith("-"))
                        TextField.setText(PROMPT);
                }
                break;
            case EQUAL:
                try {
                    TextField.setText(String.format("%.2f", calculateAnswer(exerciseStr)));
                    HistoryField.setText(HistoryField.getText() + exerciseStr + EQUAL);
                }
                catch (ArithmeticException exception){
                    TextField.setText(exception.getMessage());
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
                operatorCommandHandler(exerciseStr, btnText);
                break;
            case UNARY_MINUS:
                if (!exerciseStr.equals(PROMPT)) {
                    if(exerciseStr.startsWith(MINUS))
                        TextField.setText(exerciseStr.substring(1));
                    else
                        TextField.setText(MINUS + exerciseStr);
                }
                break;
            case POINT:
                if(!exerciseStr.contains(POINT))            /* if already contain do nothing */
                    TextField.setText(exerciseStr + btnText);
                break;
            default:
                if(isSolved){ //if solved clean the fields
                    HistoryField.setText("");
                    exerciseStr = "";
                    isSolved = false;
                }                                           /* if the operator entered, it's start of second number */
                if (usedOperator !=null && !secondNumberFlag)
                {
                    exerciseStr = "";
                    secondNumberFlag = true;
                }                                           /* if first time entered, replace the prompt */
                if(exerciseStr.equals(PROMPT))
                {
                    exerciseStr = "";
                }
                TextField.setText(exerciseStr + btnText);
                break;
        }
    }

    private double calculateAnswer(String secondNumberAsString) {
        double secondNumber = Double.parseDouble(secondNumberAsString);
        if (usedOperator == null){
            return secondNumber;
        }
        switch (usedOperator){
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

    private void operatorCommandHandler(String numAsString, String operator) {
        usedOperator = operator;
        firstNumber = Double.parseDouble(numAsString);
        HistoryField.setText(numAsString + operator);
    }
}

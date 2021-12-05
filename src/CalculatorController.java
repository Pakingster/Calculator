/**
 * Maman 13: Date 05/12/2021
 * Question 2: Calculator
 * Alexey Vartanov
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField HistoryField;

    @FXML
    private TextField TextField;

    private CalculatorObj calculator;

    @FXML
    void initialize() {
        calculator = new CalculatorObj();
    }

    @FXML
    void btnPress(ActionEvent event) {
        String btnText = ((Button) event.getSource()).getText();
        calculator.parseInput(TextField.getText(), btnText);
        TextField.setText(calculator.getOutputStr());
        HistoryField.setText(calculator.getHistoryStr());
        }
}

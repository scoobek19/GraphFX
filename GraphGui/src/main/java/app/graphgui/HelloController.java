package app.graphgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    @FXML
    private Button gGenButton, gDelButton, gSavButton;
    @FXML
    private TextField gSizeText, gWeightText;

    public void generate(ActionEvent event) {
        String[] gSize;
        Alert a;
        int s1, s2;
        try {
            gSize = gSizeText.getText().split("x");
            s1 = Integer.parseInt(gSize[0]);
            s2 = Integer.parseInt(gSize[1]);
        } catch (NumberFormatException e) {
            a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ErRoR");
            a.setHeaderText("Wrong Argument");
            a.setContentText("Wrong format of Graph size given!");
            if (a.showAndWait().get() == ButtonType.OK) {
                gSizeText.setText("200x200");
            }
        }
        
    }
}
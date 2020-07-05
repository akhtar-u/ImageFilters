package app;

import javafx.scene.control.*;

public class Controller {

    public DialogPane dialogPane;
    public Button button;

    public void showMessage(){

        dialogPane.setHeaderText("Button Works!");

        button.setDisable(true);

    }


}

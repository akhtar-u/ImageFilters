package app;

import javafx.scene.control.*;

public class Controller {

    public DialogPane dialogPane;
    public Button button;

    public void showMessage(){
        System.out.println("It works!");

        dialogPane.setHeaderText("Button Works!");


    }


}

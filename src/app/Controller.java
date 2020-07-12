package app;

import javafx.scene.control.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Controller {
    public void aboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Image Filters Application");
        alert.setContentText("Usman Akhtar © 2020");

        ButtonType VISIT = new ButtonType("Visit GitHub");

        alert.getButtonTypes().addAll(ButtonType.CLOSE, VISIT);
        alert.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CLOSE);


        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            alert.close();
        } else if (result.isPresent() && result.get() == VISIT) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/akhtar-u"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }

    }
}

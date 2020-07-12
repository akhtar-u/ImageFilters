package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Controller {

    @FXML
    BorderPane bp;
    @FXML
    ImageView image;

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
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void exitApp() {
        Platform.exit();
    }

    public void openImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );

        Stage stage = (Stage) bp.getScene().getWindow();
        File selectedImage = fileChooser.showOpenDialog(stage);

        FileInputStream inputStream = new FileInputStream(selectedImage);
        Image image = new Image(inputStream);

        this.image.setImage(image);
        this.image.setFitHeight(1000);
        this.image.setFitWidth(800);
        this.image.setPreserveRatio(true);
    }
}

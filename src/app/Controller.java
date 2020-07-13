package app;

import filters.Sharpen;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;

public class Controller {

    @FXML
    BorderPane bp;
    @FXML
    ImageView image;

    int[] currentImageData, oldImageData;
    int imgWidth, imgHeight;
    BufferedImage bImg;

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

    public void openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );

        Stage stage = (Stage) bp.getScene().getWindow();
        File selectedImage = fileChooser.showOpenDialog(stage);

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);

        this.image.setImage(image);
        this.image.setPreserveRatio(true);

        getImageData();
        imgHeight = (int) image.getHeight();
        imgWidth = (int) image.getWidth();
    }

    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Location to Save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG file", "*.png"),
                new FileChooser.ExtensionFilter("JPEG file", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF file", "*.gif")
        );

        Stage stage = (Stage) bp.getScene().getWindow();
        File selectedPath = new File(fileChooser.showSaveDialog(stage).getAbsolutePath());

        BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
        try {
            ImageIO.write(bImage, "png", selectedPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void undo() {
        setImageData(oldImageData);
    }

    public void sharpen() {
        Sharpen sharpen = new Sharpen();
        oldImageData = Arrays.copyOf(currentImageData, currentImageData.length);

        sharpen.sharpenImage(currentImageData, imgWidth);
        setImageData(currentImageData);
    }

    private void getImageData() {
        Image srcImg = image.getImage();
        bImg = SwingFXUtils.fromFXImage(srcImg, null);
        currentImageData = bImg.getRGB(0, 0, bImg.getWidth(), bImg.getHeight(), null, 0, bImg.getWidth());
    }

    private void setImageData(int[] imageData) {
        bImg.setRGB(0, 0, bImg.getWidth(), bImg.getHeight(), imageData, 0, bImg.getWidth());
        Image filteredImage = SwingFXUtils.toFXImage(bImg, null);
        image.setImage(filteredImage);
    }
}

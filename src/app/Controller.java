package app;

import data.Stack;
import filters.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
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
import java.util.Optional;

public class Controller {

    @FXML
    BorderPane bp;
    @FXML
    ImageView image;

    int[] currentImageData;
    int imgWidth, imgHeight;
    BufferedImage bImg;

    final int DEFAULT_STACK_SIZE = 10;

    String userDir = System.getProperty("user.home");
    String currentFilePath = userDir + "/Desktop";

    Sharpen sharpen = new Sharpen();
    Median median = new Median();
    Stack stack = new Stack(DEFAULT_STACK_SIZE);

    // about methods
    public void aboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Image Filters Application");
        alert.setContentText("Usman Akhtar © 2020");
        alert.initOwner(bp.getScene().getWindow());

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

    // File methods
    public void exitApp() {
        Platform.exit();
    }

    public void openImage() {
        File dirPath = new File(currentFilePath);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(dirPath);
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );

        Stage stage = (Stage) bp.getScene().getWindow();
        File selectedImage = fileChooser.showOpenDialog(stage);
        currentFilePath = selectedImage.getParent();

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);

        this.image.setImage(image);
        this.image.setPreserveRatio(true);
        stack.push(image);
        getImageData();
        imgHeight = (int) image.getHeight();
        imgWidth = (int) image.getWidth();
    }

    public void saveImage() {
        File dirPath = new File(currentFilePath);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(dirPath);
        fileChooser.setTitle("Choose Location to Save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG file", "*.png"),
                new FileChooser.ExtensionFilter("JPEG file", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG file", "*.jpg"),
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

    // Edit methods
    public void undo() {
        image.setImage(stack.popUndo());
        getImageData();
    }

    public void redo() {
        image.setImage(stack.popRedo());
        getImageData();
    }

    // Filters methods
    public void sharpen() {
        sharpen.sharpenImage(currentImageData, imgWidth);
        setImageData(currentImageData);

        stack.clear();
        stack.push(image.getImage());
    }

    public void blur() {
        TextInputDialog dialog = new TextInputDialog("1.0");
        dialog.setTitle("Gaussian Blur Filter");
        dialog.setHeaderText("Enter a decimal value between 0.0 to 5.0. Lower Sigma values = softer blur");
        dialog.setContentText("Sigma Value: ");
        dialog.initOwner(bp.getScene().getWindow());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Blur blur = new Blur(Double.parseDouble(result.get()));
            blur.blurImage(currentImageData, imgWidth);
            setImageData(currentImageData);
            stack.clear();
            stack.push(image.getImage());
        }
    }

    public void edge() {
        TextInputDialog dialog = new TextInputDialog("5.0");
        dialog.setTitle("Edge Detection Filter");
        dialog.setHeaderText("Enter a decimal value. Absolute values closer to 0.0 result in more noise and detail");
        dialog.setContentText("Weight Value: ");
        dialog.initOwner(bp.getScene().getWindow());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Edge edge = new Edge(Double.parseDouble(result.get()));
            edge.detectEdge(currentImageData, imgWidth);
            setImageData(currentImageData);

            stack.clear();
            stack.push(image.getImage());
        }
    }

    public void dither() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dither");
        alert.setHeaderText("Choose dither color palette.");
        alert.setGraphic(null);
        alert.initOwner(bp.getScene().getWindow());


        ButtonType RGBDITHER = new ButtonType("Eight Color RGB");
        ButtonType BWDITHER = new ButtonType("Black and White");
        alert.getButtonTypes().addAll(ButtonType.CLOSE, RGBDITHER, BWDITHER);
        alert.getButtonTypes().removeAll(ButtonType.OK);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            alert.close();
        } else if (result.isPresent() && result.get() == RGBDITHER) {
            Dither dither = new Dither("rgb");
            dither.ditherImage(currentImageData, imgWidth);
            setImageData(currentImageData);

            stack.clear();
            stack.push(image.getImage());
        } else if (result.isPresent() && result.get() == BWDITHER) {
            Dither dither = new Dither("bw");
            dither.ditherImage(currentImageData, imgWidth);
            setImageData(currentImageData);

            stack.clear();
            stack.push(image.getImage());
        }
    }

    public void median() {
        median.medianImage(currentImageData, imgWidth);
        setImageData(currentImageData);

        stack.clear();
        stack.push(image.getImage());
    }

    // get and set methods
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

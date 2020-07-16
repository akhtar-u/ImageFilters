package filters;

import java.awt.*;

/**
 * A filter which uses error diffusion to
 * reduce color space of an image
 *
 * @author Usman Akhtar
 */
public class Dither {


    private final Color[] colorPalette = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
            Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.white};

    public void ditherImage(int[] imageData, int imgWidth) {
        Color newColor;

        for (int i = 0; i < imageData.length; i++) {
            newColor = findNearestColor(imageData, i);
            imageData[i] = newColor.getRGB();
        }
    }

    private Color findNearestColor(int[] imageData, int index) {
        int rActual, gActual, bActual, rDiff, gDiff, bDiff, distance;
        int minDistance = 255 * 255 + 255 * 255 + 255 * 255 + 1;
        Color nearestColor = null;

        rActual = (imageData[index] & 0x00FF0000) >> 16;
        gActual = (imageData[index] & 0x0000FF00) >> 8;
        bActual = imageData[index] & 0x000000FF;

        for (int j = 0; j < colorPalette.length; j++) {

            rDiff = rActual - colorPalette[j].getRed();
            gDiff = gActual - colorPalette[j].getGreen();
            bDiff = bActual - colorPalette[j].getBlue();
            distance = rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;

            if (distance < minDistance) {
                minDistance = distance;
                nearestColor = colorPalette[j];
            }
        }
        return nearestColor;
    }
}

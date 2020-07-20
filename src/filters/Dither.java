package filters;

import java.awt.*;

/**
 * A filter which uses Floyd–Steinberg dithering algorithm to
 * reduce color space of an image.
 *
 * @author Usman Akhtar
 */
public class Dither {

    /**
     * The color palette chosen by the user.
     */
    private final Color[] chosenPalette;

    /**
     * Initializes a {@code Dither} object with the chosen
     * color palette.
     *
     * @param chosenPalette the color palette chosen by the user
     */
    public Dither(String chosenPalette) {
        if (chosenPalette.equals("rgb")) {
            Color[] RGB_REDUCED_PALETTE = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
                    Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.white};
            this.chosenPalette = RGB_REDUCED_PALETTE;
        }
        else {
            Color[] BLACK_WHITE_PALETTE = {Color.BLACK, Color.WHITE};
            this.chosenPalette = BLACK_WHITE_PALETTE;
        }
    }

    /**
     * Uses the Floyd-Steinberg dither algorithm to reduce the number of colors
     * in an image through error diffusion.
     *
     * @param imageData the array containing RGB data for the image.
     * @param imgWidth the width of the {@code Image}.
     */
    public void ditherImage(int[] imageData, int imgWidth) {
        Color actualColor, newColor;
        int errR, errG, errB, alpha;
        double red, green, blue;

        for (int i = 0; i < imageData.length; i++) {
            actualColor = new Color(imageData[i]);
            newColor = findNearestColor(imageData, i);
            alpha = FilterUtility.getAlpha(imageData[i]);

            errR = actualColor.getRed() - newColor.getRed();
            errG = actualColor.getGreen() - newColor.getGreen();
            errB = actualColor.getBlue() - newColor.getBlue();

            imageData[i] = newColor.getRGB();

            if (i + 1 < imgWidth) {
                red = roundRGB(FilterUtility.getPixel(imageData, i + 1, 0) + (7.0 / 16.0 * errR));
                green = roundRGB(FilterUtility.getPixel(imageData, i + 1, 1) + (7.0 / 16.0 * errG));
                blue = roundRGB(FilterUtility.getPixel(imageData, i + 1, 2) + (7.0 / 16.0 * errB));

                imageData[i + 1] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
            }

            if (i - 1 >= 0 && i + imgWidth < imageData.length) {
                red = roundRGB(FilterUtility.getPixel(imageData, i - 1 + imgWidth, 0) + (3.0 / 16.0 * errR));
                green = roundRGB(FilterUtility.getPixel(imageData, i - 1 + imgWidth, 1) + (3.0 / 16.0 * errG));
                blue = roundRGB(FilterUtility.getPixel(imageData, i - 1 + imgWidth, 2) + (3.0 / 16.0 * errB));

                imageData[i - 1 + imgWidth] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
            }

            if (i + imgWidth < imageData.length) {
                red = roundRGB(FilterUtility.getPixel(imageData, i + imgWidth, 0) + (5.0 / 16.0 * errR));
                green = roundRGB(FilterUtility.getPixel(imageData, i + imgWidth, 1) + (5.0 / 16.0 * errG));
                blue = roundRGB(FilterUtility.getPixel(imageData, i + imgWidth, 2) + (5.0 / 16.0 * errB));

                imageData[i + imgWidth] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
            }

            if (i + 1 < imgWidth && i + imgWidth < imageData.length) {
                red = roundRGB(FilterUtility.getPixel(imageData, i + 1 + imgWidth, 0) + (1.0 / 16.0 * errR));
                green = roundRGB(FilterUtility.getPixel(imageData, i + 1 + imgWidth, 1) + (1.0 / 16.0 * errG));
                blue = roundRGB(FilterUtility.getPixel(imageData, i + 1 + imgWidth, 2) + (1.0 / 16.0 * errB));

                imageData[i + 1 + imgWidth] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
            }
        }
    }

    /**
     *
     * @param imageData the array containing RGB data for the image.
     * @param index the index of the pixel.
     * @return the nearest color from the chosen color palette
     * to the pixel color in the image.
     */
    private Color findNearestColor(int[] imageData, int index) {
        int rActual, gActual, bActual, rDiff, gDiff, bDiff, distance;
        int minDistance = 255 * 255 + 255 * 255 + 255 * 255 + 1;
        Color nearestColor = null;
        Color[] colorPalette = chosenPalette;

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

    /**
     *
     * @param value the RGB value to be clamped.
     * @return {@code value} after clamping between (0 - 255).
     */
    private double roundRGB(double value) {
        if (value < 0) value = 0;
        else if (value > 255) value = 255;

        return value;
    }
}

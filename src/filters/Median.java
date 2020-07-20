package filters;

import java.util.Arrays;

/**
 * A filter which removes salt and pepper noise from an image
 * and smooths it while preserving edges.
 *
 * @author Usman Akhtar
 */
public class Median {

    /**
     * Initializes a {@code Median} object.
     */
    public Median() {
    }

    /**
     * Smooths the image by taking finding the median pixel value from
     * a 3x3 window around each pixel value.
     *
     * @param imageData the array containing RGB data for the image.
     * @param imgWidth  the width of the {@code Image}.
     */
    public void medianImage(int[] imageData, int imgWidth) {

        int alpha, red, green, blue;
        int[] medianImage = new int[imageData.length];


        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);

            red = getMedian(imageData, imgWidth, i, 0);
            green = getMedian(imageData, imgWidth, i, 1);
            blue = getMedian(imageData, imgWidth, i, 2);

            medianImage[i] = alpha << 24 | red << 16 | green << 8 | blue;
        }
        System.arraycopy(medianImage, 0, imageData, 0, imageData.length);
    }

    /**
     *
     * @param imageData the array containing RGB data for the image.
     * @param imgWidth the width of the {@code Image}.
     * @param i the current pixel index
     * @param color the color channel to be returned
     * @return the median color value from the 3x3 window
     */
    private int getMedian(int[] imageData, int imgWidth, int i, int color) {
        int[] pixelArray = new int[9];

        pixelArray[0] = FilterUtility.getPixel(imageData, i - imgWidth - 1, color);
        pixelArray[1] = FilterUtility.getPixel(imageData, i - imgWidth, color);
        pixelArray[2] = FilterUtility.getPixel(imageData, i - imgWidth + 1, color);
        pixelArray[3] = FilterUtility.getPixel(imageData, i - 1, color);
        pixelArray[4] = FilterUtility.getPixel(imageData, i, color);
        pixelArray[5] = FilterUtility.getPixel(imageData, i + 1, color);
        pixelArray[6] = FilterUtility.getPixel(imageData, i + imgWidth - 1, color);
        pixelArray[7] = FilterUtility.getPixel(imageData, i + imgWidth, color);
        pixelArray[8] = FilterUtility.getPixel(imageData, i + imgWidth + 1, color);

        Arrays.sort(pixelArray);

        return pixelArray[4];
    }
}


package filters;

import java.util.Arrays;

/**
 * A filter which removes noise from an image
 * and smooths it while preserving edges
 *
 * @author Usman Akhtar
 */
public class Median {

    public void medianImage(int[] imageData, int imgWidth) {

        final int BOX_SIZE = 9;

        int alpha;
        int[] medianImage = new int[imageData.length];
        int[] red = new int[BOX_SIZE];
        int[] green = new int[BOX_SIZE];
        int[] blue = new int[BOX_SIZE];

        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);

            red[0] = FilterUtility.getPixel(imageData, i - imgWidth - 1, 0);
            red[1] = FilterUtility.getPixel(imageData, i - imgWidth, 0);
            red[2] = FilterUtility.getPixel(imageData, i - imgWidth + 1, 0);
            red[3] = FilterUtility.getPixel(imageData, i - 1, 0);
            red[4] = FilterUtility.getPixel(imageData, i, 0);
            red[5] = FilterUtility.getPixel(imageData, i + 1, 0);
            red[6] = FilterUtility.getPixel(imageData, i + imgWidth - 1, 0);
            red[7] = FilterUtility.getPixel(imageData, i + imgWidth, 0);
            red[8] = FilterUtility.getPixel(imageData, i + imgWidth + 1, 0);

            green[0] = FilterUtility.getPixel(imageData, i - imgWidth - 1, 1);
            green[1] = FilterUtility.getPixel(imageData, i - imgWidth, 1);
            green[2] = FilterUtility.getPixel(imageData, i - imgWidth + 1, 1);
            green[3] = FilterUtility.getPixel(imageData, i - 1, 1);
            green[4] = FilterUtility.getPixel(imageData, i, 1);
            green[5] = FilterUtility.getPixel(imageData, i + 1, 1);
            green[6] = FilterUtility.getPixel(imageData, i + imgWidth - 1, 1);
            green[7] = FilterUtility.getPixel(imageData, i + imgWidth, 1);
            green[8] = FilterUtility.getPixel(imageData, i + imgWidth + 1, 1);

            blue[0] = FilterUtility.getPixel(imageData, i - imgWidth - 1, 2);
            blue[1] = FilterUtility.getPixel(imageData, i - imgWidth, 2);
            blue[2] = FilterUtility.getPixel(imageData, i - imgWidth + 1, 2);
            blue[3] = FilterUtility.getPixel(imageData, i - 1, 2);
            blue[4] = FilterUtility.getPixel(imageData, i, 2);
            blue[5] = FilterUtility.getPixel(imageData, i + 1, 2);
            blue[6] = FilterUtility.getPixel(imageData, i + imgWidth - 1, 2);
            blue[7] = FilterUtility.getPixel(imageData, i + imgWidth, 2);
            blue[8] = FilterUtility.getPixel(imageData, i + imgWidth + 1, 2);

            Arrays.sort(red);
            Arrays.sort(green);
            Arrays.sort(blue);

            medianImage[i] = alpha << 24 | red[4] << 16 | green[4] << 8 | blue[4];
        }

        System.arraycopy(medianImage, 0, imageData, 0, imageData.length);
    }

}


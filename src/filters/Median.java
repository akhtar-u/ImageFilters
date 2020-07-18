package filters;

import java.util.ArrayList;
import java.util.List;

/**
 * A filter which removes noise from an image
 * and smooths it while preserving edges
 *
 * @author Usman Akhtar
 */
public class Median {

    public void medianImage(int[] imageData, int imgWidth) {

        double red, green, blue;
        int alpha;
        int[] medianImage = new int[imageData.length];
        List<Integer> rList = new ArrayList<Integer>();
        List<Integer> gList = new ArrayList<Integer>();
        List<Integer> bList = new ArrayList<Integer>();


        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);

            red = FilterUtility.getPixel(imageData, i - imgWidth - 1, 0);
            red += FilterUtility.getPixel(imageData, i - imgWidth, 0);
            red += FilterUtility.getPixel(imageData, i - imgWidth + 1, 0);
            red += FilterUtility.getPixel(imageData, i - 1, 0);
            red += FilterUtility.getPixel(imageData, i, 0);
            red += FilterUtility.getPixel(imageData, i + 1, 0);
            red += FilterUtility.getPixel(imageData, i + imgWidth - 1, 0);
            red += FilterUtility.getPixel(imageData, i + imgWidth, 0);
            red += FilterUtility.getPixel(imageData, i + imgWidth + 1, 0);

            green = FilterUtility.getPixel(imageData, i - imgWidth - 1, 1);
            green += FilterUtility.getPixel(imageData, i - imgWidth, 1);
            green += FilterUtility.getPixel(imageData, i - imgWidth + 1, 1);
            green += FilterUtility.getPixel(imageData, i - 1, 1);
            green += FilterUtility.getPixel(imageData, i, 1);
            green += FilterUtility.getPixel(imageData, i + 1, 1);
            green += FilterUtility.getPixel(imageData, i + imgWidth - 1, 1);
            green += FilterUtility.getPixel(imageData, i + imgWidth, 1);
            green += FilterUtility.getPixel(imageData, i + imgWidth + 1, 1);

            blue = FilterUtility.getPixel(imageData, i - imgWidth - 1, 2);
            blue += FilterUtility.getPixel(imageData, i - imgWidth, 2);
            blue += FilterUtility.getPixel(imageData, i - imgWidth + 1, 2);
            blue += FilterUtility.getPixel(imageData, i - 1, 2);
            blue += FilterUtility.getPixel(imageData, i, 2);
            blue += FilterUtility.getPixel(imageData, i + 1, 2);
            blue += FilterUtility.getPixel(imageData, i + imgWidth - 1, 2);
            blue += FilterUtility.getPixel(imageData, i + imgWidth, 2);
            blue += FilterUtility.getPixel(imageData, i + imgWidth + 1, 2);

            medianImage[i] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
        }
    }
}


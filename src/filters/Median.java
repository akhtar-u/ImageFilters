package filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A filter which removes noise from an image
 * and smooths it while preserving edges
 *
 * @author Usman Akhtar
 */
public class Median {

    public void medianImage(int[] imageData, int imgWidth) {

        int alpha;
        int[] medianImage = new int[imageData.length];
        int[] red = new int[9];
        int[] green = new int[9];
        int[] blue = new int[9];

        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);




            medianImage[i] = alpha << 24 | red[4] << 16 | green[4] << 8 | blue[4];
        }

        System.arraycopy(medianImage, 0, imageData, 0, imageData.length);
    }

}


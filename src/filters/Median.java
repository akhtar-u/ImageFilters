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

            for (int j = i - 1; j < i + 2; j++) {
                rList.add(FilterUtility.getPixel(imageData, j, 0));

            }

            for (int k = i - imgWidth - 1; k < i - imgWidth + 2; k++) {

            }

            for (int l = i + imgWidth - 1; l < i + imgWidth + 2; l++) {

            }


            //medianImage[i] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
        }

    }

    private int getMedian(List<Integer> colorList){
        int median = 0;

        return median;
    }


}


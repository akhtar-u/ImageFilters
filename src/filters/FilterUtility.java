package filters;

/**
 * Utility methods to use with other image filter classes
 *
 * @author Usman Akhtar
 */
public final class FilterUtility {

    public static double getPixel(int[] data, int index, int color) {
        double newPixel;

        if (index < 0) {
            index = 0;
        } else if (index >= data.length) {
            index = data.length - 1;
        }

        if (color == 0) {
            newPixel = (data[index] & 0x00FF0000) >> 16;
        } else if (color == 1) {
            newPixel = (data[index] & 0x0000FF00) >> 8;
        } else {
            newPixel = data[index] & 0x000000FF;
        }

        return newPixel;
    }

    public static int getAlpha(int data) {

        return (data >> 24) & 0xff;
    }


}

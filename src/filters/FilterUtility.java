package filters;

/**
 * Utility methods to use with other image filter classes.
 *
 * @author Usman Akhtar
 */
public final class FilterUtility {

    /**
     * @param data  the array containing RGB data for the image.
     * @param index the index of the pixel.
     * @param color the color to be returned.
     * @return the RGB value (0 - 255) at the given pixel {@code index} for
     * the given {@code color} from the image {@code data}.
     */
    public static int getPixel(int[] data, int index, int color) {
        int newPixel;

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

    /**
     * @param data the pixel's RGB data.
     * @return the isolated alpha channel from the pixel's RGB data.
     */
    public static int getAlpha(int data) {

        return (data >> 24) & 0xff;
    }


}

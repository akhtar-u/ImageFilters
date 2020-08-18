package filters;

/**
 * Utility methods to use with other image filter classes.
 *
 * @author Usman Akhtar
 */
public final class FilterUtility {

    /**
     * Private constructor to prevent instances of {@code FilterUtility}
     * from being created
     */
    private FilterUtility() {
    }

    /**
     * @param imageData the array containing RGB data for the image.
     * @param imgWidth  the width of the {@code Image}.
     * @param i         the current pixel index
     * @param color     the color channel to be returned
     * @param kernel    the kernel to be used during the convulation
     * @return the value for the target pixel after convulation with the specified kernel
     */
    public static double convolve(int[] imageData, int imgWidth, int i, int color, double[] kernel) {
        double pixelData;

        pixelData = FilterUtility.getPixel(imageData, i - imgWidth - 1, color) * kernel[0];
        pixelData += FilterUtility.getPixel(imageData, i - imgWidth, color) * kernel[1];
        pixelData += FilterUtility.getPixel(imageData, i - imgWidth + 1, color) * kernel[2];
        pixelData += FilterUtility.getPixel(imageData, i - 1, color) * kernel[3];
        pixelData += FilterUtility.getPixel(imageData, i, color) * kernel[4];
        pixelData += FilterUtility.getPixel(imageData, i + 1, color) * kernel[5];
        pixelData += FilterUtility.getPixel(imageData, i + imgWidth - 1, color) * kernel[6];
        pixelData += FilterUtility.getPixel(imageData, i + imgWidth, color) * kernel[7];
        pixelData += FilterUtility.getPixel(imageData, i + imgWidth + 1, color) * kernel[8];

        return pixelData;
    }

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

    /**
     * @param value the RGB value to be clamped.
     * @return {@code value} after clamping between (0 - 255).
     */
    public static double clampRGB(double value) {
        if (value < 0) value = 0;
        if (value > 255) value = 255;

        return value;
    }
}

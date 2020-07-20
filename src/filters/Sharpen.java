package filters;

/**
 * A filter which sharpens an image.
 *
 * @author Usman Akhtar
 */
public class Sharpen {

    /**
     * The {@code Kernel} object to be used during convulation.
     */
    public Kernel kernel;

    /**
     * Initializes a {@code Sharpen} object with a sharpen {@code Kernel}.
     */
    public Sharpen() {
        kernel = new Kernel("sharpen");
    }

    /**
     * Sharpens the image by highlighting the "details" of the image
     * using a sharpen {@code Kernel}.
     *
     * @param imageData the array containing RGB data for the image.
     * @param imgWidth  the width of the {@code Image}.
     */
    public void sharpenImage(int[] imageData, int imgWidth) {

        int alpha;
        double red, green, blue;
        double[] sharpKernel = kernel.getKernelArray();

        int[] sharpenImage = new int[imageData.length];

        for (int i = 0; i < imageData.length; i++) {
            alpha = FilterUtility.getAlpha(imageData[i]);

            red = FilterUtility.convolve(imageData, imgWidth, i, 0, sharpKernel);
            green = FilterUtility.convolve(imageData, imgWidth, i, 1, sharpKernel);
            blue = FilterUtility.convolve(imageData, imgWidth, i, 2, sharpKernel);

            red = FilterUtility.clampRGB(red);
            green = FilterUtility.clampRGB(green);
            blue = FilterUtility.clampRGB(blue);

            sharpenImage[i] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
        }
        System.arraycopy(sharpenImage, 0, imageData, 0, imageData.length);
    }
}

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

        double[] sharpKernel = kernel.getKernelArray();
        int[] argbData = FilterUtility.convolve(imageData, imgWidth, sharpKernel);

        System.arraycopy(argbData, 0, imageData, 0, imageData.length);
    }
}

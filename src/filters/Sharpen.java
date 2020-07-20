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

    public void sharpenImage(int[] imageData, int imgWidth) {

        double red, green, blue;
        int alpha;
        double[] sharpKernel = kernel.getKernelArray();

        int[] sharpenImage = new int[imageData.length];

        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);

            red = FilterUtility.getPixel(imageData, i - imgWidth - 1, 0) * sharpKernel[0];
            red += FilterUtility.getPixel(imageData, i - imgWidth, 0) * sharpKernel[1];
            red += FilterUtility.getPixel(imageData, i - imgWidth + 1, 0) * sharpKernel[2];
            red += FilterUtility.getPixel(imageData, i - 1, 0) * sharpKernel[3];
            red += FilterUtility.getPixel(imageData, i, 0) * sharpKernel[4];
            red += FilterUtility.getPixel(imageData, i + 1, 0) * sharpKernel[5];
            red += FilterUtility.getPixel(imageData, i + imgWidth - 1, 0) * sharpKernel[6];
            red += FilterUtility.getPixel(imageData, i + imgWidth, 0) * sharpKernel[7];
            red += FilterUtility.getPixel(imageData, i + imgWidth + 1, 0) * sharpKernel[8];

            green = FilterUtility.getPixel(imageData, i - imgWidth - 1, 1) * sharpKernel[0];
            green += FilterUtility.getPixel(imageData, i - imgWidth, 1) * sharpKernel[1];
            green += FilterUtility.getPixel(imageData, i - imgWidth + 1, 1) * sharpKernel[2];
            green += FilterUtility.getPixel(imageData, i - 1, 1) * sharpKernel[3];
            green += FilterUtility.getPixel(imageData, i, 1) * sharpKernel[4];
            green += FilterUtility.getPixel(imageData, i + 1, 1) * sharpKernel[5];
            green += FilterUtility.getPixel(imageData, i + imgWidth - 1, 1) * sharpKernel[6];
            green += FilterUtility.getPixel(imageData, i + imgWidth, 1) * sharpKernel[7];
            green += FilterUtility.getPixel(imageData, i + imgWidth + 1, 1) * sharpKernel[8];

            blue = FilterUtility.getPixel(imageData, i - imgWidth - 1, 2) * sharpKernel[0];
            blue += FilterUtility.getPixel(imageData, i - imgWidth, 2) * sharpKernel[1];
            blue += FilterUtility.getPixel(imageData, i - imgWidth + 1, 2) * sharpKernel[2];
            blue += FilterUtility.getPixel(imageData, i - 1, 2) * sharpKernel[3];
            blue += FilterUtility.getPixel(imageData, i, 2) * sharpKernel[4];
            blue += FilterUtility.getPixel(imageData, i + 1, 2) * sharpKernel[5];
            blue += FilterUtility.getPixel(imageData, i + imgWidth - 1, 2) * sharpKernel[6];
            blue += FilterUtility.getPixel(imageData, i + imgWidth, 2) * sharpKernel[7];
            blue += FilterUtility.getPixel(imageData, i + imgWidth + 1, 2) * sharpKernel[8];

            red = FilterUtility.clampRGB(red);
            green = FilterUtility.clampRGB(green);
            blue = FilterUtility.clampRGB(blue);

            sharpenImage[i] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
        }
        System.arraycopy(sharpenImage, 0, imageData, 0, imageData.length);
    }
}

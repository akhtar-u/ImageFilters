package filters;

/**
 * A filter which adds a Gaussian Blur to an image.
 *
 * @author Usman Akhtar
 */
public class Blur {

    /**
     * The {@code Kernel} object to be used during convulation
     * with the given {@code sigma} value;
     */
    public Kernel kernel;
    private double sigma;

    /**
     * Initializes a {@code Sharpen} object with a sharpen {@code Kernel}
     *
     * @param sigma the sigma value to calculate the Gaussian blur {@code Kernel}
     */
    public Blur(double sigma) {
        this.sigma = sigma;
        kernel = new Kernel(this.sigma);
    }

    public void blurImage(int[] imageData, int imgWidth) {

        double red, green, blue;
        int alpha;

        int[] convolveX = new int[imageData.length];
        int[] convolveY = new int[imageData.length];
        double[] blurKernel = kernel.getKernelArray();
        int kLength = blurKernel.length / 2;


        for (int i = 0; i < imgWidth; i++) {
            for (int j = i; j < imageData.length; j += imgWidth) {

                alpha = (imageData[i] >> 24) & 0xff;

                red = (FilterUtility.getPixel(imageData, j, 0)) * blurKernel[kLength];
                green = (FilterUtility.getPixel(imageData, j, 1)) * blurKernel[kLength];
                blue = (FilterUtility.getPixel(imageData, j, 2)) * blurKernel[kLength];

                for (int k = kLength; k > 0; k--) {
                    red += (FilterUtility.getPixel(imageData, j - k * imgWidth, 0)) * blurKernel[kLength - k];
                    red += (FilterUtility.getPixel(imageData, j + k * imgWidth, 0)) * blurKernel[kLength + k];

                    green += (FilterUtility.getPixel(imageData, j - k * imgWidth, 1)) * blurKernel[kLength - k];
                    green += (FilterUtility.getPixel(imageData, j + k * imgWidth, 1)) * blurKernel[kLength + k];

                    blue += (FilterUtility.getPixel(imageData, j - k * imgWidth, 2)) * blurKernel[kLength - k];
                    blue += (FilterUtility.getPixel(imageData, j + k * imgWidth, 2)) * blurKernel[kLength + k];
                }
                convolveX[j] = alpha << 24| (int) red << 16 | (int) green << 8 | (int) blue;
            }
        }

        //repeat for the other dimension
        for (int i = 0; i < imageData.length; i += imgWidth) {
            for (int j = i; j < i + imgWidth; j++) {

                alpha = (imageData[i] >> 24) & 0xff;

                red = (FilterUtility.getPixel(convolveX, j, 0)) * blurKernel[kLength];
                green = (FilterUtility.getPixel(convolveX, j, 1)) * blurKernel[kLength];
                blue = (FilterUtility.getPixel(convolveX, j, 2)) * blurKernel[kLength];

                for (int k = kLength; k > 0; k--) {
                    red += (FilterUtility.getPixel(convolveX, j - k, 0)) * blurKernel[kLength - k];
                    red += (FilterUtility.getPixel(convolveX, j + k, 0)) * blurKernel[kLength + k];

                    green += (FilterUtility.getPixel(convolveX, j - k, 1)) * blurKernel[kLength - k];
                    green += (FilterUtility.getPixel(convolveX, j + k, 1)) * blurKernel[kLength + k];

                    blue += (FilterUtility.getPixel(convolveX, j - k, 2)) * blurKernel[kLength - k];
                    blue += (FilterUtility.getPixel(convolveX, j + k, 2)) * blurKernel[kLength + k];
                }
                convolveY[j] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
            }
        }
        //store the result back to the original array
        System.arraycopy(convolveY, 0, imageData, 0, imageData.length);

    }
}

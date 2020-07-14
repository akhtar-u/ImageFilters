package filters;

/**
 * A filter which adds a Gaussian Blur to an image.
 */
public class Blur {

    /**
     * The {@code Kernel} object to be used during convulation
     * with the given {@code sigma} value;
     */
    public Kernel kernel;
    public double sigma;

    /**
     * Initializes a {@code Sharpen} object with a sharpen {@code Kernel}
     *
     * @param sigma the sigma value to calculate the Gaussian blur {@code Kernel}
     */
    public Blur(double sigma) {
        kernel = new Kernel(sigma);
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

                red = (getPixel(imageData, j, 0)) * blurKernel[kLength];
                green = (getPixel(imageData, j, 1)) * blurKernel[kLength];
                blue = (getPixel(imageData, j, 2)) * blurKernel[kLength];

                for (int k = kLength; k > 0; k--) {
                    red += (getPixel(imageData, j - k * imgWidth, 0)) * blurKernel[kLength - k];
                    red += (getPixel(imageData, j + k * imgWidth, 0)) * blurKernel[kLength + k];

                    green += (getPixel(imageData, j - k * imgWidth, 1)) * blurKernel[kLength - k];
                    green += (getPixel(imageData, j + k * imgWidth, 1)) * blurKernel[kLength + k];

                    blue += (getPixel(imageData, j - k * imgWidth, 2)) * blurKernel[kLength - k];
                    blue += (getPixel(imageData, j + k * imgWidth, 2)) * blurKernel[kLength + k];
                }
                convolveX[j] = alpha << 24| (int) red << 16 | (int) green << 8 | (int) blue;
            }
        }

        //repeat for the other dimension
        for (int i = 0; i < imageData.length; i += imgWidth) {
            for (int j = i; j < i + imgWidth; j++) {

                alpha = (imageData[i] >> 24) & 0xff;

                red = (getPixel(convolveX, j, 0)) * blurKernel[kLength];
                green = (getPixel(convolveX, j, 1)) * blurKernel[kLength];
                blue = (getPixel(convolveX, j, 2)) * blurKernel[kLength];

                for (int k = kLength; k > 0; k--) {
                    red += (getPixel(convolveX, j - k, 0)) * blurKernel[kLength - k];
                    red += (getPixel(convolveX, j + k, 0)) * blurKernel[kLength + k];

                    green += (getPixel(convolveX, j - k, 1)) * blurKernel[kLength - k];
                    green += (getPixel(convolveX, j + k, 1)) * blurKernel[kLength + k];

                    blue += (getPixel(convolveX, j - k, 2)) * blurKernel[kLength - k];
                    blue += (getPixel(convolveX, j + k, 2)) * blurKernel[kLength + k];
                }
                convolveY[j] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
            }
        }
        //store the result back to the original array
        System.arraycopy(convolveY, 0, imageData, 0, imageData.length);

    }

    private double getPixel(int[] data, int index, int color) {
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
}

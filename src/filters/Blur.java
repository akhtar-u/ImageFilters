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
        double[] blurKernel = kernel.getKernelArray();

        int[] blurImage = new int[imageData.length];

        for (int i = 0; i < imageData.length; i++) {

            alpha = (imageData[i] >> 24) & 0xff;

            red = (getPixel(imageData, i - imgWidth - 1, 0)) * blurKernel[0];
            red += (getPixel(imageData, i - imgWidth, 0)) * blurKernel[1];
            red += (getPixel(imageData, i - imgWidth + 1, 0)) * blurKernel[2];
            red += (getPixel(imageData, i - 1, 0)) * blurKernel[3];
            red += getPixel(imageData, i, 0) * blurKernel[4];
            red += (getPixel(imageData, i + 1, 0)) * blurKernel[5];
            red += (getPixel(imageData, i + imgWidth - 1, 0)) * blurKernel[6];
            red += (getPixel(imageData, i + imgWidth, 0)) * blurKernel[7];
            red += (getPixel(imageData, i + imgWidth + 1, 0)) * blurKernel[8];

            green = (getPixel(imageData, i - imgWidth - 1, 1)) * blurKernel[0];
            green += (getPixel(imageData, i - imgWidth, 1)) * blurKernel[1];
            green += (getPixel(imageData, i - imgWidth + 1, 1)) * blurKernel[2];
            green += (getPixel(imageData, i - 1, 1)) * blurKernel[3];
            green += getPixel(imageData, i, 1) * blurKernel[4];
            green += (getPixel(imageData, i + 1, 1)) * blurKernel[5];
            green += (getPixel(imageData, i + imgWidth - 1, 1)) * blurKernel[6];
            green += (getPixel(imageData, i + imgWidth, 1)) * blurKernel[7];
            green += (getPixel(imageData, i + imgWidth + 1, 1)) * blurKernel[8];

            blue = (getPixel(imageData, i - imgWidth - 1, 2)) * blurKernel[0];
            blue += (getPixel(imageData, i - imgWidth, 2)) * blurKernel[1];
            blue += (getPixel(imageData, i - imgWidth + 1, 2)) * blurKernel[2];
            blue += (getPixel(imageData, i - 1, 2)) * blurKernel[3];
            blue += getPixel(imageData, i, 2) * blurKernel[4];
            blue += (getPixel(imageData, i + 1, 2)) * blurKernel[5];
            blue += (getPixel(imageData, i + imgWidth - 1, 2)) * blurKernel[6];
            blue += (getPixel(imageData, i + imgWidth, 2)) * blurKernel[7];
            blue += (getPixel(imageData, i + imgWidth + 1, 2)) * blurKernel[8];

            blurImage[i] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
        }

        System.arraycopy(blurImage, 0, imageData, 0, imageData.length);

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

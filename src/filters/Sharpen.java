package filters;

/**
 * A filter which sharpens an image.
 */
public class Sharpen {

    /**
     * The {@code Kernel} object to be used during convulation
     */
    public Kernel kernel;

    /**
     * Initializes a {@code Sharpen} object with a sharpen {@code Kernel}
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

            alpha = (imageData[i] >> 24) & 0xff;

            red = (getPixel(imageData, i - imgWidth - 1, 0)) * sharpKernel[0];
            red += (getPixel(imageData, i - imgWidth, 0)) * sharpKernel[1];
            red += (getPixel(imageData, i - imgWidth + 1, 0)) * sharpKernel[2];
            red += (getPixel(imageData, i - 1, 0)) * sharpKernel[3];
            red += getPixel(imageData, i, 0) * sharpKernel[4];
            red += (getPixel(imageData, i + 1, 0)) * sharpKernel[5];
            red += (getPixel(imageData, i + imgWidth - 1, 0)) * sharpKernel[6];
            red += (getPixel(imageData, i + imgWidth, 0)) * sharpKernel[7];
            red += (getPixel(imageData, i + imgWidth + 1, 0)) * sharpKernel[8];

            green = (getPixel(imageData, i - imgWidth - 1, 1)) * sharpKernel[0];
            green += (getPixel(imageData, i - imgWidth, 1)) * sharpKernel[1];
            green += (getPixel(imageData, i - imgWidth + 1, 1)) * sharpKernel[2];
            green += (getPixel(imageData, i - 1, 1)) * sharpKernel[3];
            green += getPixel(imageData, i, 1) * sharpKernel[4];
            green += (getPixel(imageData, i + 1, 1)) * sharpKernel[5];
            green += (getPixel(imageData, i + imgWidth - 1, 1)) * sharpKernel[6];
            green += (getPixel(imageData, i + imgWidth, 1)) * sharpKernel[7];
            green += (getPixel(imageData, i + imgWidth + 1, 1)) * sharpKernel[8];

            blue = (getPixel(imageData, i - imgWidth - 1, 2)) * sharpKernel[0];
            blue += (getPixel(imageData, i - imgWidth, 2)) * sharpKernel[1];
            blue += (getPixel(imageData, i - imgWidth + 1, 2)) * sharpKernel[2];
            blue += (getPixel(imageData, i - 1, 2)) * sharpKernel[3];
            blue += getPixel(imageData, i, 2) * sharpKernel[4];
            blue += (getPixel(imageData, i + 1, 2)) * sharpKernel[5];
            blue += (getPixel(imageData, i + imgWidth - 1, 2)) * sharpKernel[6];
            blue += (getPixel(imageData, i + imgWidth, 2)) * sharpKernel[7];
            blue += (getPixel(imageData, i + imgWidth + 1, 2)) * sharpKernel[8];

            if (red < 0) red = 0;
            if (green < 0) green = 0;
            if (blue < 0) blue = 0;
            if (red > 255) red = 255;
            if (green > 255) green = 255;
            if (blue > 255) blue = 255;

            sharpenImage[i] = alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
        }

        System.arraycopy(sharpenImage, 0, imageData, 0, imageData.length);

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

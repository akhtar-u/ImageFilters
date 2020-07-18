package filters;


/**
 * A filter which detects the "edges" of an image
 * and displays it in black and white
 *
 * @author Usman Akhtar
 */
public class Edge {

    /**
     * The {@code Kernel} object to be used during convulation
     */
    public Kernel kernel;
    private double weight;

    /**
     * Initializes an {@code Edge} object with an edge detection {@code Kernel}
     */
    public Edge(double weight) {
        kernel = new Kernel("edge");
        this.weight = weight;
    }

    public void detectEdge(int[] imageData, int imgWidth) {

        double red, green, blue, avg;
        int alpha;
        double[] sharpKernel = kernel.getKernelArray();
        int[] detectEdge = new int[imageData.length];


        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);

            red = (FilterUtility.getPixel(imageData, i - imgWidth - 1, 0)) * sharpKernel[0];
            red += (FilterUtility.getPixel(imageData, i - imgWidth, 0)) * sharpKernel[1];
            red += (FilterUtility.getPixel(imageData, i - imgWidth + 1, 0)) * sharpKernel[2];
            red += (FilterUtility.getPixel(imageData, i - 1, 0)) * sharpKernel[3];
            red += FilterUtility.getPixel(imageData, i, 0) * sharpKernel[4];
            red += (FilterUtility.getPixel(imageData, i + 1, 0)) * sharpKernel[5];
            red += (FilterUtility.getPixel(imageData, i + imgWidth - 1, 0)) * sharpKernel[6];
            red += (FilterUtility.getPixel(imageData, i + imgWidth, 0)) * sharpKernel[7];
            red += (FilterUtility.getPixel(imageData, i + imgWidth + 1, 0)) * sharpKernel[8];

            green = (FilterUtility.getPixel(imageData, i - imgWidth - 1, 1)) * sharpKernel[0];
            green += (FilterUtility.getPixel(imageData, i - imgWidth, 1)) * sharpKernel[1];
            green += (FilterUtility.getPixel(imageData, i - imgWidth + 1, 1)) * sharpKernel[2];
            green += (FilterUtility.getPixel(imageData, i - 1, 1)) * sharpKernel[3];
            green += FilterUtility.getPixel(imageData, i, 1) * sharpKernel[4];
            green += (FilterUtility.getPixel(imageData, i + 1, 1)) * sharpKernel[5];
            green += (FilterUtility.getPixel(imageData, i + imgWidth - 1, 1)) * sharpKernel[6];
            green += (FilterUtility.getPixel(imageData, i + imgWidth, 1)) * sharpKernel[7];
            green += (FilterUtility.getPixel(imageData, i + imgWidth + 1, 1)) * sharpKernel[8];

            blue = (FilterUtility.getPixel(imageData, i - imgWidth - 1, 2)) * sharpKernel[0];
            blue += (FilterUtility.getPixel(imageData, i - imgWidth, 2)) * sharpKernel[1];
            blue += (FilterUtility.getPixel(imageData, i - imgWidth + 1, 2)) * sharpKernel[2];
            blue += (FilterUtility.getPixel(imageData, i - 1, 2)) * sharpKernel[3];
            blue += FilterUtility.getPixel(imageData, i, 2) * sharpKernel[4];
            blue += (FilterUtility.getPixel(imageData, i + 1, 2)) * sharpKernel[5];
            blue += (FilterUtility.getPixel(imageData, i + imgWidth - 1, 2)) * sharpKernel[6];
            blue += (FilterUtility.getPixel(imageData, i + imgWidth, 2)) * sharpKernel[7];
            blue += (FilterUtility.getPixel(imageData, i + imgWidth + 1, 2)) * sharpKernel[8];

            if (red < 0) red = 0;
            if (green < 0) green = 0;
            if (blue < 0) blue = 0;
            if (red > 255) red = 255;
            if (green > 255) green = 255;
            if (blue > 255) blue = 255;

            red = FilterUtility.getPixel(imageData, i, 0) - red;
            green = FilterUtility.getPixel(imageData, i, 1) - green;
            blue = FilterUtility.getPixel(imageData, i, 2) - blue;

            avg = (.2126 * red + 0.7152* green + 0.0722* blue) / weight;

            detectEdge[i] = alpha << 24 | (int) avg << 16 | (int) avg << 8 | (int) avg;
        }

        System.arraycopy(detectEdge, 0, imageData, 0, imageData.length);
    }
}

package filters;


/**
 * A filter which detects the "edges" of an image
 * and displays it in black and white.
 *
 * @author Usman Akhtar
 */
public class Edge {

    /**
     * The {@code Kernel} object to be used during convulation
     * with the given weight value.
     */
    public Kernel kernel;
    private double weight;

    /**
     * Initializes an {@code Edge} object with an edge detection {@code Kernel}
     * and provided {@code weight} value.
     *
     * @param weight the value of detail to be included in the filtered image.
     */
    public Edge(double weight) {
        kernel = new Kernel("edge");
        this.weight = weight;
    }

    /**
     * Shows the details of the image in black and white
     * by using an edge detection {@code Kernel}.
     *
     * @param imageData the array containing RGB data for the image.
     * @param imgWidth the width of the {@code Image}.
     */
    public void detectEdge(int[] imageData, int imgWidth) {

        double red, green, blue, avg;
        int alpha;
        double[] edgeKernel = kernel.getKernelArray();
        int[] detectEdge = new int[imageData.length];


        for (int i = 0; i < imageData.length; i++) {

            alpha = FilterUtility.getAlpha(imageData[i]);

            red = (FilterUtility.getPixel(imageData, i - imgWidth - 1, 0)) * edgeKernel[0];
            red += (FilterUtility.getPixel(imageData, i - imgWidth, 0)) * edgeKernel[1];
            red += (FilterUtility.getPixel(imageData, i - imgWidth + 1, 0)) * edgeKernel[2];
            red += (FilterUtility.getPixel(imageData, i - 1, 0)) * edgeKernel[3];
            red += FilterUtility.getPixel(imageData, i, 0) * edgeKernel[4];
            red += (FilterUtility.getPixel(imageData, i + 1, 0)) * edgeKernel[5];
            red += (FilterUtility.getPixel(imageData, i + imgWidth - 1, 0)) * edgeKernel[6];
            red += (FilterUtility.getPixel(imageData, i + imgWidth, 0)) * edgeKernel[7];
            red += (FilterUtility.getPixel(imageData, i + imgWidth + 1, 0)) * edgeKernel[8];

            green = (FilterUtility.getPixel(imageData, i - imgWidth - 1, 1)) * edgeKernel[0];
            green += (FilterUtility.getPixel(imageData, i - imgWidth, 1)) * edgeKernel[1];
            green += (FilterUtility.getPixel(imageData, i - imgWidth + 1, 1)) * edgeKernel[2];
            green += (FilterUtility.getPixel(imageData, i - 1, 1)) * edgeKernel[3];
            green += FilterUtility.getPixel(imageData, i, 1) * edgeKernel[4];
            green += (FilterUtility.getPixel(imageData, i + 1, 1)) * edgeKernel[5];
            green += (FilterUtility.getPixel(imageData, i + imgWidth - 1, 1)) * edgeKernel[6];
            green += (FilterUtility.getPixel(imageData, i + imgWidth, 1)) * edgeKernel[7];
            green += (FilterUtility.getPixel(imageData, i + imgWidth + 1, 1)) * edgeKernel[8];

            blue = (FilterUtility.getPixel(imageData, i - imgWidth - 1, 2)) * edgeKernel[0];
            blue += (FilterUtility.getPixel(imageData, i - imgWidth, 2)) * edgeKernel[1];
            blue += (FilterUtility.getPixel(imageData, i - imgWidth + 1, 2)) * edgeKernel[2];
            blue += (FilterUtility.getPixel(imageData, i - 1, 2)) * edgeKernel[3];
            blue += FilterUtility.getPixel(imageData, i, 2) * edgeKernel[4];
            blue += (FilterUtility.getPixel(imageData, i + 1, 2)) * edgeKernel[5];
            blue += (FilterUtility.getPixel(imageData, i + imgWidth - 1, 2)) * edgeKernel[6];
            blue += (FilterUtility.getPixel(imageData, i + imgWidth, 2)) * edgeKernel[7];
            blue += (FilterUtility.getPixel(imageData, i + imgWidth + 1, 2)) * edgeKernel[8];

            red = FilterUtility.getPixel(imageData, i, 0) - red;
            green = FilterUtility.getPixel(imageData, i, 1) - green;
            blue = FilterUtility.getPixel(imageData, i, 2) - blue;

            avg = (.2126 * red + 0.7152 * green + 0.0722 * blue) / weight;

            detectEdge[i] = alpha << 24 | (int) avg << 16 | (int) avg << 8 | (int) avg;
        }
        System.arraycopy(detectEdge, 0, imageData, 0, imageData.length);
    }
}

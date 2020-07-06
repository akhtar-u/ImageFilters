package filters;

/**
 * A 3x3 kernel in 1-dimension for Sharpen and Gaussian
 * Blur image filters.
 */
public class Kernel {

    /**
     * The type of kernel
     */
    public final String kernelType;
    private double[] kernelArray;

    /**
     * Initializes a kernel with a {@code kernelSize} as
     * specified by the caller.     *
     *
     * @param kernelType the type of kernel to initialize ("blur" or "sharpen")
     * @throws IllegalArgumentException if {@code kernelType} is not one
     *                                  of "blur" or "sharpen" or "edge"
     */
    public Kernel(String kernelType) {

        if (kernelType.toLowerCase() != "sharpen" || kernelType.toLowerCase() != "blur" || kernelType.toLowerCase() != "edge") {
            throw new IllegalArgumentException();
        } else if (kernelType.toLowerCase() == "blur") {
            kernelArray = blurKernel();
        } else if (kernelType.toLowerCase() == "sharpen") {
            kernelArray = sharpenKernel();
        } else if (kernelType.toLowerCase() == "edge") {
            kernelArray = edgeKernel();
        }

        this.kernelType = kernelType;
    }


    /**
     * @return the type of this {@code Kernel}
     */
    public String getKernelType() {
        return kernelType;
    }

    /**
     * @return the data of this {@code Kernel} as a 1-D array
     */
    public double[] getKernelArray() {
        return kernelArray;
    }

    /**
     * @return a 1-D array for a Gaussian blur kernel
     */
    private double[] blurKernel() {

        return null;
    }

    /**
     * @return a 1-D array for a sharpen kernel
     */
    private double[] sharpenKernel() {

        return new double[]{-1d, -1d, -1d, -1d, 9d, -1d, -1d, -1d, -1d};
    }

    /**
     *
     * @return a 1-D array for an edge detection kernel (details of image)
     */
    private double[] edgeKernel() {
        return new double[]{1/9d, 1/9d, 1/9d, 1/9d, 1/9d, 1/9d, 1/9d, 1/9d, 1/9d};
    }
}

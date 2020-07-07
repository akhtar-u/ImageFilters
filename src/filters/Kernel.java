package filters;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.DoubleStream;

/**
 * A 3x3 kernel in 1-dimension for Sharpen and Gaussian
 * Blur image filters.
 */
public class Kernel {

    /**
     * The type of kernel
     */
    public final String kernelType;
    public double sigma;
    private final double[] kernelArray;


    /**
     * Initializes a kernel with a {@code kernelType} as
     * specified by the caller.
     *
     * @param kernelType the type of kernel to initialize
     *                   ("blur" or "sharpen" or "edge")
     *
     * @throws IllegalArgumentException if {@code kernelType} is not one
     *                                  of "blur" or "sharpen" or "edge"
     */
    public Kernel(String kernelType) {

        sigma = 1.0;

        if (kernelType.equals("blur")) {
            kernelArray = blurKernel(sigma);
        } else if (kernelType.equals("sharpen")) {
            kernelArray = sharpenKernel();
        } else if (kernelType.equals("edge")) {
            kernelArray = edgeKernel();
        }
        else {
            throw new IllegalArgumentException();
        }

        this.kernelType = kernelType;
    }

    /**
     * Initializes a Gaussian blur {@code Kernel} with the specified
     * {@code sigma} value
     *
     * @param sigma the sigma value for the Gaussian function
     *              larger values lead to a stronger blur
     * @throws IllegalArgumentException if {@code sigma} is less than 0.0
     *                                  or greater than 5.0
     */
    public Kernel(double sigma) {
        if (sigma < 0.0 || sigma > 5.0) {
            throw new IllegalArgumentException();
        }

        this.sigma = sigma;
        kernelType = "blur";
        kernelArray = blurKernel(sigma);
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
     * @return the {@code sigma} value of the blue {@code Kernel}
     *
     * @throws IllegalAccessError if {@code kernelType} is not "blur"
     */
    public double getBlurSigma() {
        if (!this.getKernelType().equals("blur")){
            throw new IllegalAccessError();
        }
        return sigma;
    }

    /**
     * @return a String object representing the {@code Kernel}
     */
    @Override
    public String toString() {

        if (kernelType == "blur"){
            return "Kernel Type: " + kernelType + "\n" +
                    "Sigma: " + sigma + "\n" +
                    "Kernel Data: " + Arrays.toString(kernelArray);
        }
        else {
            return "Kernel Type: " + kernelType + "\n" +
                    "Kernel Data: " + Arrays.toString(kernelArray);
        }
    }

    /**
     *
     * @param sigma the sigma value for the Gaussian function
     * @return a 1-D array for a blur kernel
     * by using the Gaussian function in 1-D
     */
    private double[] blurKernel(double sigma) {
        final int KERNEL_LENGTH = 9;
        final double GAUSS_SQRT = Math.sqrt((2 * Math.PI * Math.pow(sigma, 2d)));
        double kernelValue, sum;
        double[] blurKernel = new double[KERNEL_LENGTH];

        for (int i = 0; i < KERNEL_LENGTH / 2; i++) {
            kernelValue = Math.exp(-1 * (Math.pow(KERNEL_LENGTH / 2 - i, 2)) / (2 * Math.pow(sigma, 2d))) /
                    GAUSS_SQRT;

            blurKernel[i] = kernelValue;
            blurKernel[KERNEL_LENGTH - 1 - i] = kernelValue;
        }

        kernelValue = Math.exp(-1 * (Math.pow(0, 2)) / (2 * Math.pow(sigma, 2))) /
                GAUSS_SQRT;
        blurKernel[KERNEL_LENGTH / 2] = kernelValue;
        sum = DoubleStream.of(blurKernel).sum();

        for (int j = 0; j < KERNEL_LENGTH; j++) {
            blurKernel[j] /= sum;
        }

        return blurKernel;
    }

    /**
     * @return a 1-D array for a sharpen kernel
     */
    private double[] sharpenKernel() {
        return new double[]{-1d, -1d, -1d, -1d, 9d, -1d, -1d, -1d, -1d};
    }

    /**
     * @return a 1-D array for an edge detection kernel (details of image)
     */
    private double[] edgeKernel() {
        return new double[]{1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d};
    }
}

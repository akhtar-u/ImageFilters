package filters;

/**
 * A kernel matrix in 1-dimension for Sharpen and Gaussian
 * Blur image filters.
 */
public class Kernel {

    /**
     * The size of the kernel
     */
    public final int kernelSize;

    /**
     * Initializes a kernel with size 9 (3 x 3)
     */
    public Kernel (){
        kernelSize = 9;
    }

    /**
     * Initializes a kernel with a {@code kernelSize} as
     * specified by the caller.
     *
     * @param kernelSize the size of the kernel
     * @throws IllegalArgumentException if {@code kernelSize}
     * is not odd and greater than 3
     */
    public Kernel(int kernelSize) {
        this.kernelSize = kernelSize;
    }
}

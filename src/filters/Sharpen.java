package filters;

/**
 * A filter which sharpens an image.
 */
public class Sharpen {

    public Kernel kernel;

    /**
     * Initializes a {@code Sharpen} object with a sharpen {@code Kernel}
     */
    public Sharpen() {
        kernel = new Kernel("sharpen");
    }

    public double[] sharpenImage (double[] imageData, int imgWidth){


        return null;
    }


}

package test;


import filters.Kernel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


public class KernelTest {

    @Test
    void testConstructorEdge() {
        double[] arr = {1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d};
        Kernel myK = new Kernel("edge");
        String error = String.format("Kernel Constructor(%s) or getKernelArray failed ", Arrays.toString(arr));

        assertNotSame(arr, myK.getKernelArray(), error);
        assertArrayEquals(arr, myK.getKernelArray(), error);
    }

    @Test
    void testConstructorSharpen() {
        double[] arr = {-1d, -1d, -1d, -1d, 9d, -1d, -1d, -1d, -1d};
        Kernel myK = new Kernel("sharpen");
        String error = String.format("Kernel Constructor(%s) or getKernelArray failed ", Arrays.toString(arr));

        assertNotSame(arr, myK.getKernelArray(), error);
        assertArrayEquals(arr, myK.getKernelArray(), error);
    }

    @Test
    void testBlurSigma() {
        double actualSig = 2.0;
        Kernel myK = new Kernel(2.0);
        String error = ("Kernel Constructor or getBlurSigma failed");

        assertEquals(actualSig, myK.getBlurSigma(), error);
    }

    @Test
    void testKernelType() {
        String type = "blur";
        Kernel myK = new Kernel(type);
        String error = ("Kernel Constructor or getKernelType failed");

        assertEquals(type, myK.getKernelType(), error);
    }

    @Test
    void testToStringBlur() {
        Kernel myK = new Kernel("blur");
        String output = "Kernel Type: " + myK.getKernelType() + "\n" +
                                    "Sigma: " + myK.getBlurSigma() + "\n" +
                                    "Kernel Data: " + Arrays.toString(myK.getKernelArray());
        String error = ("toString output is different");

        assertEquals(output, myK.toString(), error);
    }

    @Test
    void testToStringEdgeorSharpen() {
        Kernel myK = new Kernel("edge");
        String output = "Kernel Type: " + myK.getKernelType() + "\n" +
                "Kernel Data: " + Arrays.toString(myK.getKernelArray());

        String error = ("toString output is different");
        assertEquals(output, myK.toString(), error);
    }

    // test exceptions

    @Test
    void testBlurSigmaException() {
        Kernel myK = new Kernel("edge");

        try {
            myK.getBlurSigma();
            String error = String.format("getBlurSigma() failed to throw an exception");
            fail(error);
        } catch (IllegalAccessError e) {
            // ok
        } catch (Exception e) {
            fail("wrong type of exception thrown");
        }
    }

    @Test
    void testKernelConstrException() {
        String type = "blurr";

        try {
            Kernel myK = new Kernel(type);
            String error = String.format("Kernel constructor failed to throw an exception");
            fail(error);
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("wrong type of exception thrown");
        }
    }

    @Test
    void testBlurConstrException() {
        double sigma1 = -0.5;
        double sigma2 = 5.5;

        try {
            Kernel myK1 = new Kernel(sigma1);
            String error = String.format("Kernel constructor failed to throw an exception");
            fail(error);
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("wrong type of exception thrown");
        }

        try {
            Kernel myK2 = new Kernel(sigma2);
            String error = String.format("Kernel constructor failed to throw an exception");
            fail(error);
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("wrong type of exception thrown");
        }
    }
}

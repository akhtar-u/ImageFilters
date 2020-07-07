package test;


import filters.Kernel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;


public class KernelTest {

    @Test
    void testKernelConstructor1() {
        double[] arr = {1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d, 1 / 9d};
        Kernel myK = new Kernel("edge");
        String error = String.format("Kernel Constructor(%s) or getKernelArray failed ", Arrays.toString(arr));
        assertFalse(myK.getKernelArray() == arr, error);
        assertTrue(Arrays.equals(myK.getKernelArray(), arr), error);
    }


}

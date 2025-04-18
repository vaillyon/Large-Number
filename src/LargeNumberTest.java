
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LargeNumberTest {

    // checks small numbers
    @Test
    public void testSmallMultiplication() {
        String num1 = "110";  // Binary for 6
        String num2 = "101";  // Binary for 5
        String expected = "11110";  // Binary for 30

        String result = LargeNumber.multiply(num1, num2);
//compare output
        Assertions.assertEquals(expected, result);
    }

    // large numbers
    @Test
    public void testLargeMultiplication() {
        String num1 = "1100110011001100110011001100110011";
        String num2 = "1010101010101010101010101010101010";

        String expected = "10001000100010001000100010001000010111011101110111011101110111011110";
        String actual = LargeNumber.multiply(num1, num2);
//10001000100010001000100010001000010111011101110111011101110111011110_2
        System.out.println(expected);
        System.out.println(actual);
// compare with answer
        Assertions.assertEquals(expected, actual);
    }
}


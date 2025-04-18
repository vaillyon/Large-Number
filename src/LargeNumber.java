import java.util.Random;

public class LargeNumber {

    // Prevent instantiation
    private LargeNumber() {
    }

    // Multiply
    public static String multiply(String x, String y) {
        System.out.println("Multiplying: " + x + " * " + y);

        // Get max length
        int n = Math.max(x.length(), y.length());

        // Pad to make lengths equal
        x = padBinary(x, n);
        y = padBinary(y, n);

        System.out.println("Padded inputs: " + x + ", " + y);

        // Base case:
        if (n <= 1) {
            int result = Integer.parseInt(x, 2) * Integer.parseInt(y, 2);
            System.out.println("Base case result: " + Integer.toBinaryString(result));
            return Integer.toBinaryString(result);
        }

        // Find midpoint
        int mid = n / 2;
        System.out.println("Splitting at: " + mid);

        // Split
        String x1 = x.substring(0, x.length() - mid);
        String x0 = x.substring(x.length() - mid);
        String y1 = y.substring(0, y.length() - mid);
        String y0 = y.substring(y.length() - mid);

        System.out.println("x1: " + x1 + ", x0: " + x0 + ", y1: " + y1 + ", y0: " + y0);

        // Recursion
        String z0 = multiply(x0, y0);
        String z2 = multiply(x1, y1);

        String z1 = subtractBinary(
                multiply(addBinary(x1, x0), addBinary(y1, y0)),
                addBinary(z2, z0)
        );

        System.out.println("z0: " + z0 + ", z1: " + z1 + ", z2: " + z2);

        // Shift z2 by 2 * mid
        String part1 = shiftLeft(z2, 2 * mid);
        String part2 = shiftLeft(z1, mid);

        // Combine
        String result = addBinary(addBinary(part1, part2), z0);

        System.out.println("part1: " + part1 + ", part2: " + part2);
        System.out.println("Final result before trimming: " + result);

        // Trim leading zeros
        result = trimLeadingZeros(result);
        System.out.println("Final result after trimming: " + result);

        return result;
    }

    // Pad binary strings with leading zeros
    private static String padBinary(String binary, int length) {
        StringBuilder binaryBuilder = new StringBuilder(binary);
        while (binaryBuilder.length() < length) {
            binaryBuilder.insert(0, "0");
        }
        return binaryBuilder.toString();
    }

    // Add the two
    private static String addBinary(String a, String b) {
        System.out.println("Adding: " + a + " + " + b);
        int maxLength = Math.max(a.length(), b.length());
        a = padBinary(a, maxLength);
        b = padBinary(b, maxLength);

        StringBuilder result = new StringBuilder();
        int carry = 0;

        for (int i = maxLength - 1; i >= 0; i--) {
            int sum = (a.charAt(i) - '0') + (b.charAt(i) - '0') + carry;
            carry = sum / 2;
            result.insert(0, sum % 2);
        }

        // Add left carry to the result
        if (carry > 0) {
            result.insert(0, carry);
        }

        System.out.println("Addition result: " + result);
        return result.toString();
    }

    // Subtract
    private static String subtractBinary(String a, String b) {
        System.out.println("Subtracting: " + a + " - " + b);
        int maxLength = Math.max(a.length(), b.length());
        a = padBinary(a, maxLength);
        b = padBinary(b, maxLength);

        StringBuilder result = new StringBuilder();
        int borrow = 0;

        for (int i = maxLength - 1; i >= 0; i--) {
            int digitA = a.charAt(i) - '0';
            int digitB = b.charAt(i) - '0';
            int diff = digitA - digitB - borrow;

            if (diff < 0) {
                diff += 2; // Handle borrow
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.insert(0, diff);
        }

        // Trim leading zeros
        String finalResult = trimLeadingZeros(result.toString());
        System.out.println("Subtraction result: " + finalResult);
        return finalResult;
    }

    // Shift to left by a number
    private static String shiftLeft(String binary, int n) {
        String result = binary + "0".repeat(Math.max(0, n));
        System.out.println("Shifting " + binary + " left by " + n + ": " + result);
        return result;
    }

    // Trim leading zeros
    private static String trimLeadingZeros(String binary) {
        int index = 0;
        while (index < binary.length() - 1 && binary.charAt(index) == '0') {
            index++;
        }
        return binary.substring(index);
    }

    // Generate random binary number of a given length
    private static String generateRandomBinary(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(2));  // Random 0 or 1
        }
        return sb.toString();
    }

    // Main method to test the multiplication and measure execution times
    public static void main(String[] args) {
        // Number sizes to test
        int[] sizes = {4, 8, 16, 32, 64, 128, 256, 512, 1024};

        // Store execution times
        long[] times = new long[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            String num1 = generateRandomBinary(sizes[i]);
            String num2 = generateRandomBinary(sizes[i]);

            System.out.println("Multiplying numbers of size: " + sizes[i] + " bits");

            // Start timer
            long startTime = System.nanoTime();

            // Perform multiplication
            multiply(num1, num2);

            // End timer
            long endTime = System.nanoTime();

            // time in milliseconds
            times[i] = (endTime - startTime) / 1_000_000;

            System.out.println("Size: " + sizes[i] + " bits, Time: " + times[i] + " ms");
        }

        // Display results
        System.out.println("Execution times (in ms):");
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Size: " + sizes[i] + " bits, Time: " + times[i] + " ms");
        }
    }
}



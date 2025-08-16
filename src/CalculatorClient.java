import java.rmi.Naming;

/**
 * Simple client program to test the Calculator RMI service.
 * It connects to the server, sends numbers and operations,
 * and prints the results to the console.
 */
public class CalculatorClient {
    public static void main(String[] args) {
        try {
            // Connect to the remote Calculator service running on localhost
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");
            System.out.println("Connected to CalculatorService.");

            // --- Test 1: Push values and perform 'max' operation ---
            calc.pushValue(12);  // Push first number
            calc.pushValue(8);   // Push second number
            calc.pushValue(20);  // Push third number
            calc.pushOperation("max");  // Ask the server to find the maximum
            int result = calc.pop();  // Get the result back from the server
            System.out.println("Result of max operation: " + result);  // Should be 20

            // --- Test 2: Try the delayPop method ---
            calc.pushValue(15);
            System.out.println("Waiting 1.5 seconds before pop...");
            int delayed = calc.delayPop(1500);  // Wait before popping
            System.out.println("Delayed pop result: " + delayed);

            // --- Test 3: Check if the stack is empty ---
            System.out.println("Stack empty? " + calc.isEmpty());

        } catch (Exception e) {
            // Catch any errors that happen while connecting or calling methods
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
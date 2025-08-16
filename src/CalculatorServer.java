import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * This class starts up the Calculator RMI server.
 * It creates the registry and makes the Calculator service available
 * so clients can find and use it.
 */
public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on the default port (1099)
            // Without this, clients won’t be able to find our service
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry started.");

            // Create an instance of the calculator (the actual logic lives here)
            Calculator calculator = new CalculatorImplementation();

            // Register (bind) our calculator so clients can call it remotely
            Naming.rebind("CalculatorService", calculator);
            System.out.println("CalculatorServer is ready.");
        } catch (Exception e) {
            // If something goes wrong (like port issues or binding errors),
            // print it out so we know what happened
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

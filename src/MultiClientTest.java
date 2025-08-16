import java.rmi.Naming;

/**
 * This program is used to test the Calculator RMI service with multiple clients at once.
 * It creates several threads, and each thread acts like its own client.
 * This helps us check if the server really handles more than one client at the same time.
 */
public class MultiClientTest {

    public static void main(String[] args) {
        int numberOfClients = 4; // You can increase this number
        Thread[] clients = new Thread[numberOfClients];

        // Start up each client in its own thread
        for (int i = 0; i < numberOfClients; i++) {
            final int clientId = i + 1;
            clients[i] = new Thread(() -> runClient(clientId));
            clients[i].start();
        }

        // Wait for all clients to finish
        for (Thread client : clients) {
            try {
                client.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Test interrupted.");
            }
        }

        System.out.println("Multi-client test completed.");
    }
    // Each thread (client) runs this method
    private static void runClient(int clientId) {
        try {
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");
            System.out.println("[Client " + clientId + "] Connected to CalculatorService.");

            // Each client pushes one value
            calc.pushValue(clientId * 10);
            System.out.println("[Client " + clientId + "] Pushed value: " + (clientId * 10));

            // Small delay so pushes interleave
            Thread.sleep(500);

            // First client performs max operation
            if (clientId == 1) {
                System.out.println("[Client " + clientId + "] Performing 'max' operation...");
                calc.pushOperation("max");
                int result = calc.pop();
                System.out.println("[Client " + clientId + "] Result of max operation: " + result);
            }

            // The other clients check if stack is empty
            if (clientId != 1) {
                boolean empty = calc.isEmpty();
                System.out.println("[Client " + clientId + "] Stack empty? " + empty);
            }

        } catch (Exception e) {
            System.err.println("[Client " + clientId + "] Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

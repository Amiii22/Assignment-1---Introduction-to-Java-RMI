import java.rmi.Remote;
import java.rmi.RemoteException;

// This is the interface that defines the calculator operations.
// It just declares what can be done, the server will actually do the work.
public interface Calculator extends Remote {

    /**
     * Put a number on top of the shared stack.
     * @param val Integer value to push.
     * @throws RemoteException if a remote communication error occurs.
     */
    void pushValue(int val) throws RemoteException;

    /**
     * Do an operation ("min", "max", "lcm", "gcd")
     * using the numbers that are already on the stack.
     * After calculating, it puts the result back on the stack.
     * @param operator the operation to run
     */
    void pushOperation(String operator) throws RemoteException;

    /**
     * Take the number from the top of the stack and return it.
     * If the stack is empty, it’ll throw an error.
     * @return the number you removed
     */
    int pop() throws RemoteException;

    /**
     * Check if the stack has no numbers in it.
     * @return true if the stack is empty, false if it still has numbers
     */
    boolean isEmpty() throws RemoteException;

    /**
     * Wait for a bit before popping the top value off the stack.
     * Handy if you want a delay before getting the result.
     * @param millis how long to wait (in milliseconds)
     * @return the number popped after the delay
     */
    int delayPop(int millis) throws RemoteException;
}
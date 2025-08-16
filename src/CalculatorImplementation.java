import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;
import java.util.ArrayList;

// This class is where the real "calculator brain" lives.
// It actually does the work for the methods listed in the Calculator interface.
// All clients share the same stack here.
public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

    // The shared stack (all clients use this one).
    // It stores numbers (Integers). Operations just work on these values.
    private final Stack<Object> stack;

    // Constructor - initializes the shared stack.
    public CalculatorImplementation() throws RemoteException {
        super();
        stack = new Stack<>();
    }

    // Put a number on top of the shared stack.
    @Override
    public synchronized void pushValue(int val) throws RemoteException {
        stack.push(val);
        System.out.println("Value pushed: " + val);
    }

    // Do an operation ("min", "max", "lcm", "gcd") on the stack values.
    // It pulls off all the numbers currently on the stack, calculates the result,
    // and then puts that result back on the stack.
    @Override
    public synchronized void pushOperation(String operator) throws RemoteException {
        if (stack.isEmpty()) {
            System.out.println("Operation requested but stack is empty.");
            return;
        }

        ArrayList<Integer> values = new ArrayList<>();
        // Collect all numbers from the stack
        while (!stack.isEmpty() && stack.peek() instanceof Integer) {
            values.add((Integer) stack.pop());
        }

        if (values.isEmpty()) {
            System.out.println("No integer values on stack to operate on.");
            return;
        }

        int result = values.get(0);
        switch (operator.toLowerCase()) {
            case "min":
                for (int val : values) result = Math.min(result, val);
                break;
            case "max":
                for (int val : values) result = Math.max(result, val);
                break;
            case "lcm":
                for (int i = 1; i < values.size(); i++) result = lcm(result, values.get(i));
                break;
            case "gcd":
                for (int i = 1; i < values.size(); i++) result = gcd(result, values.get(i));
                break;
            default:
                System.out.println("Invalid operation requested: " + operator);
                return;
        }

        stack.push(result);
        System.out.println("Operation '" + operator + "' performed, result pushed: " + result);
    }

    // Take the number off the top of the stack and return it.
    @Override
    public synchronized int pop() throws RemoteException {
        if (stack.isEmpty()) {
            throw new RemoteException("Cannot pop from empty stack");
        }
        Object top = stack.pop();
        if (top instanceof Integer) {
            System.out.println("Popped value: " + top);
            return (Integer) top;
        } else {
            throw new RemoteException("Top of stack is not an integer");
        }
    }

    // Check if the stack has no numbers left.
    @Override
    public synchronized boolean isEmpty() throws RemoteException {
        return stack.isEmpty();
    }

    // Wait for a given time before removing and returning the top number.
    @Override
    public int delayPop(int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RemoteException("Interrupted during delayPop", e);
        }
        return pop();
    }

    // Helper method: greatest common divisor (GCD).
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Helper method: least common multiple (LCM).
    private int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }
}


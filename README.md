# Java RMI Calculator Assignment

Hey there! This project is a simple Java RMI-based calculator. The server keeps a stack of numbers and lets clients do things like push values, pop them, and run operations like `min`, `max`, `lcm`, and `gcd`. You can also simulate multiple clients accessing the stack at the same time.  

---

## What’s in this project

- **Calculator.java** – The interface that defines what the server can do.  
- **CalculatorImplementation.java** – The server code that actually handles all the operations.  
- **CalculatorServer.java** – Starts the RMI registry and makes the server available to clients.  
- **CalculatorClient.java** – A simple client to test pushing, popping, and running operations.  
- **MultiClientTest.java** – Lets you test multiple clients at the same time to see how the server handles them.  

---

## How to compile

Open a terminal in your project folder and run:

```bash
javac *.java
```
---

## How to start the server

1. Start the RMI registry (default port 1099):

```bash
rmiregistry &
```
- The & runs it in the background.
- Make sure you are in the directory containing the compiled .class files.

2. Run the server:

```bash
java CalculatorServer
```

- You should see messages like:

```bash
RMI Registry started.
CalculatorServer is ready.
```
---

## How to run a client

In a new terminal (still in the same project folder):

```bash
java CalculatorClient
```
This client will:
- Push some numbers onto the stack
- Perform an operation (like max)
- Pop the result
- Test the delayed pop (delayPop)
- Check if the stack is empty

---

## How to run multiple clients

To see how the server handles multiple clients at the same time:

```bash
java MultiClientTest
```
- his will start several client threads.
- Each client pushes values and interacts with the stack.
- The first client performs a max operation while others check if the stack is empty.
- The terminal output shows each client’s actions and results.



















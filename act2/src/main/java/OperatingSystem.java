public class OperatingSystem {
    private Queue<Process> readyQueue = new Queue<>();
    private Stack<Process> historyStack = new Stack<>();

    public void addProcess(int pid, String name) {
        readyQueue.enqueue(new Process(pid, name));
    }

    public Process runNext() {
        Process process = readyQueue.dequeue();
        if (process == null) {
            return null;
        }
        historyStack.push(process);
        return process;
    }

    public Process peekReady() {
        return readyQueue.peek();
    }

    public Process peekLastFinished() {
        return historyStack.peek();
    }

    public String readyQueueString() {
        return readyQueue.toString();
    }

    public String historyString() {
        return historyStack.toString();
    }
}

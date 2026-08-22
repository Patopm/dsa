public class Process {
    private int pid;
    private String name;

    public Process(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return pid + ":" + name;
    }
}

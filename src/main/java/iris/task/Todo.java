package iris.task;

public class Todo extends Task {
    public Todo(String name) {
        super(name);
    }

    @Override
    public String storageFormat() {
        return String.join("|", "T", super.storageFormat()) + "\n";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

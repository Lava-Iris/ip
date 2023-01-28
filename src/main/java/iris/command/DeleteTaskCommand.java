package iris.command;

import iris.TaskList;
import iris.TaskStore;
import iris.Ui;
import iris.exception.IrisException;
import iris.exception.UnknownTaskException;
import iris.task.Task;

public class DeleteTaskCommand extends Command {
    private final int i;
    public DeleteTaskCommand(int i) {
        this.i = i;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, TaskStore taskStore) throws IrisException {
        Task task;
        try {
            task = tasks.get(i);
            tasks.remove(i);
        } catch (IndexOutOfBoundsException e) {
            throw new UnknownTaskException();
        }
        taskStore.updateTasks(tasks);
        Ui.output("I've removed this task");
        Ui.output(task.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DeleteTaskCommand) {
            DeleteTaskCommand dtc = (DeleteTaskCommand) o;
            return this.i == dtc.i;
        } else {
            return false;
        }
    }
}

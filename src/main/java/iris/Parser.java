package iris;

import iris.command.*;
import iris.exception.MissingFieldException;
import iris.task.Todo;

import iris.exception.IrisException;
import iris.exception.NoTaskException;

import iris.task.Deadline;
import iris.task.Event;

public class Parser {
    enum CommandWord {
        help, bye, list, mark, unmark, delete, todo, deadline, event, reset, filter, find
    }

    private static void checkNotEmpty(String str, String message) throws MissingFieldException {
        if (str.isEmpty()) {
            throw new MissingFieldException(message);
        }
    }
    public static Command parse(String input) throws IrisException {
        String[] arr;
        String name, from, to, by;
        CommandWord command;
        try {
            command = CommandWord.valueOf(input.split(" ")[0]);
        } catch (IllegalArgumentException e) {
            throw new NoTaskException();
        }
        switch (command) {
        case help:
            return new HelpCommand();
        case bye:
            return new ExitCommand();
        case list:
            return new ListCommand();
        case mark:
            return new MarkTaskCommand(Integer.parseInt(input.split(" ")[1]) - 1);
        case unmark:
            return new UnmarkTaskCommand(Integer.parseInt(input.split(" ")[1]) - 1);
        case delete:
            return new DeleteTaskCommand(Integer.parseInt(input.split(" ")[1]) - 1);
        case reset:
            return new ResetCommand();
        case filter:
            try {
                String s = input.substring(7);
                String[] a = s.split("/to");
                if (a.length == 1) {
                    return new FilterCommand(s.trim());
                } else {
                    return new FilterCommand(a[0].trim(), a[1].trim());
                }
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("Date for filter");
            }
        case find:
            try {
                return new FindCommand(input.substring(5).trim());
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("keyword for find");
            }
        case todo:
            try {
                name = input.substring(5).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("Description of Todo task");
            }
            checkNotEmpty(name, "Description of Todo task");
            return new AddTaskCommand(new Todo(name));
        case deadline:
            try {
                arr = input.split("/by");
                name = arr[0].substring(9).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("Description of deadline");
            }
            checkNotEmpty(name, "Description of deadline");
            try {
                by = arr[1].substring(1).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("Deadline date");
            }
            checkNotEmpty(by, "Deadline date");
            return new AddTaskCommand(new Deadline(name, by));
        case event:
            try {
                arr = input.split("/");
                name = arr[0].substring(6).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("Description of event");
            }
            checkNotEmpty(name, "Description of event");
            try {
                from = arr[1].substring(5).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("Start date of event");
            }
            checkNotEmpty(from, "Start date of event");
            try {
                to = arr[2].substring(3).trim();
            } catch (IndexOutOfBoundsException e) {
                throw new MissingFieldException("End date of event");
            }
            checkNotEmpty(to, "End date of event");
            return new AddTaskCommand(new Event(name, from, to));
        default:
            throw new NoTaskException();
        }
    }
}

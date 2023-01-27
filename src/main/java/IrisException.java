public class IrisException extends Exception{
    private String message;
    public IrisException() {
        this.message = "OOPS!!! Something went wrong." + " Type \"help\" to see the commands.";
    }

    public IrisException(String message) {
        this.message = "OOPS!!! " + message + " Type \"help\" to see the commands.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}

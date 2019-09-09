/**
 * An abstract Task class to create more specific tasks.
 */

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public abstract String getType();

    public abstract String getDate();

    public abstract boolean getStatus();

    /**
     * Returns a tick or cross based on have the task been done
     *
     * @return tick or cross
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public String getDescription() {
        return description;
    }

    public void changeStatusTrue() {
        this.isDone = true;
    }


}
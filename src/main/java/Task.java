// Taken from hint
abstract public class Task {
  protected final String description;
  private boolean isDone;

  public Task(String description) {
    this.description = description;
    this.isDone = false;
  }

  public String getStatusIcon() {
    return (this.isDone ? "X" : " "); // mark done task with X
  }

  public void markAsDone() {
    this.isDone = true;
  }

  public void markAsNotDone() {
    this.isDone = false;
  }

  @Override
  public String toString() {
    return "[" + getStatusIcon() + "] " + this.description;
  }
}
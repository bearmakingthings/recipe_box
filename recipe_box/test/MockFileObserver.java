import model.File;

// A mock to check that file builders are communicating with their observer.
public class MockFileObserver implements model.FileObserver {
  private StringBuilder log;

  public MockFileObserver() {
    this.log = new StringBuilder();
  }
  public void notifyNew(File file, int parent) {
    this.log.append("New ")
            .append(file.getName()).append(" ")
            .append(file.getId()).append(" ")
            .append("parent ").append(parent);
  }

  public void notifyMoved(int file, int newParent) {
    this.log.append("Moved ")
            .append(file).append(" ")
            .append("to ").append(newParent);
  }

  public String getLog() {
    return this.log.toString();
  }
}

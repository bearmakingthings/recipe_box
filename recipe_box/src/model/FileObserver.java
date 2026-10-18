package model;

// Notify the model of updates to files.
public interface FileObserver {
  // Add a newly created File to the model.
  void notifyNew(File file, int parent);

  // Update the directory tree when a file is moved.
  void notifyMoved(int file, int newParent);


}

package model;

// Notify the model of updates to files.
public interface FileObserver {
  // Notify the model of a new File.
  void notifyNew(int id, int parent, RecipeBuilder rec);
  void notifyNew(int id, int parent, DirectoryBuilder dir);

  // Inform the model that a file was moved.
  void notifyMoved(int file, int newParent);

  // Inform the model that a file's details were changed.
  void notifyUpdated(File file);

}

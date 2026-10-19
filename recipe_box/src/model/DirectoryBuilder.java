package model;

// Create a new Directory.
public interface DirectoryBuilder {
  DirectoryBuilder setName(String newName);
  DirectoryBuilder setParent(int d_id);
  void submit() throws RuntimeException;
  Directory build(int id) throws IllegalStateException;
}

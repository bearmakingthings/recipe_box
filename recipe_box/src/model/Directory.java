package model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

// A directory, which may contain any number of other Files.
public class Directory extends File {
  Map<Integer, File> contents;

  // Lazy-load a Directory, without its children.
  protected Directory(int id, String name) {
    super(id, name);
    this.contents = new TreeMap<>();
  }

  // Add a file to this Directory's contents and update its parent to this.
  void addFile(File child) throws IllegalArgumentException {
    int childId = child.id;
    // If the file is a duplicate, raise an error.
    if (this.contents.get(childId) != null) {
      throw new IllegalArgumentException("Directory already contains file with id "
              + String.valueOf(childId));
    }

    this.contents.put(childId, child);

    child.setParent(this);
  }
}

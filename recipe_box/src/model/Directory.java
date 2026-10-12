package model;

import java.util.Map;

// A directory, which may contain any number of other Files.
public class Directory extends File {
  Map<Integer, File> contents;

  // Lazy-load a Directory, without its children.
  protected Directory(int id, String name) {
    super(id, name);
  }

}

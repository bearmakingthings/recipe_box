package model;

// A file, which may be a directory or a recipe.
public abstract class File {
  int id;
  Directory parent;
  String name;

  // TODO:
  // - will need name-based recursive path resolution for methods that return file ids.

}

package model;

// A file, which may be a directory or a recipe.
public abstract class File {
  protected final int id;
  protected String name;
  protected Directory parent;

  // Initialize this file, unaware of its parent Directory.
  protected File(int id, String name) {
    this.id = id;
    this.name = name;
    this.parent = null;
  }

  // Set the parent directory of this File.
  void setParent(Directory parent) {
    this.parent = parent;
  }

  // TODO MAYBE DELETE GETTERS, OR REDUCE VISIBILITY, DEPENDING ON WHO NEEDS THEM.
  // getName is probably going to be useful to UIs, though?

  // Return the name of this File.
  public String getName() {
    return this.name;
  }

  // Return this File's ID.
  public int getId() {
    return this.id;
  }

}

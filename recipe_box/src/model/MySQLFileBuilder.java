package model;

// Common data and behavior across builders for different filetypes.
class MySQLFileBuilder {
  MySQLDB db;
  FileObserver obs;
  Integer parent;
  String name;

  MySQLFileBuilder(MySQLDB db, FileObserver obs) {
    this.db = db;
    this.obs = obs;
    this.parent = null;
    this.name = null;
  }

  void setName(String newName) {
    this.name = newName;
  }

  void setParent(int d_id) {
    this.parent = d_id;
  }

  // Check that the file's name and parent are not null.
  protected void checkSubmitConditions() {
    if (this.name == null) {
      throw new IllegalStateException("A file must have a name.");
    }
    if (this.parent == null) {
      throw new IllegalStateException("A file must have a parent directory.");
    }
  }


}

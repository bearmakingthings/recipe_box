package model;

// Build a new directory and insert it into the database.
public class MySQLDirectoryBuilder implements DirectoryBuilder {
  MySQLFileBuilder fBdr;

  MySQLDirectoryBuilder(MySQLDB db, FileObserver obs) {
    this.fBdr = new MySQLFileBuilder(db, obs);
  }

  public DirectoryBuilder setName(String newName) {
    this.fBdr.setName(newName);
    return this;
  }

  public DirectoryBuilder setParent(int d_id) {
    this.fBdr.setParent(d_id);
    return this;
  }

  // Submit this new directory to be added to the database.
  public void submit() throws RuntimeException {
    fBdr.checkSubmitConditions();
    fBdr.db.submitNewDirectory(this);
  }

  // Create a new Directory object from this builder.
  public Directory build(int id) throws IllegalStateException {
    return new Directory(id, fBdr.name);
  }
}

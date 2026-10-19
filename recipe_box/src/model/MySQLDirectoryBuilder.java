package model;

// TODO
public class MySQLDirectoryBuilder implements DirectoryBuilder {

  public MySQLDirectoryBuilder() {
  }

  MySQLDirectoryBuilder(MySQLDB db, FileObserver obs) {
  }

  public DirectoryBuilder setName(String newName) {
    return null;
  }

  public DirectoryBuilder setParent(int d_id) {
    return null;
  }

  public void submit() throws RuntimeException {

  }

  public Directory build(int id) throws IllegalStateException {
    return null;
  }
}

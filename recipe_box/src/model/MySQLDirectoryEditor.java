package model;

public class MySQLDirectoryEditor extends MySQLDirectoryBuilder {
  protected Directory editing;

  MySQLDirectoryEditor(MySQLDB db, FileObserver obs, Directory editing) {
    super(db, obs);
    this.editing = editing;
  }

  // TODO
  @Override
  public void submit() throws RuntimeException {
  }
}

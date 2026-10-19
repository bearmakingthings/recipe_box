package model;

import java.util.List;

public class RecipeBoxImp implements RecipeBox, FileObserver, TagObserver {

  // The open database.
  private final Database db;

  // The set of all tags.
  private TagSet tags;

  // The root directory.
  private Directory root;

  // The working directory.
  private Directory workingDir;

  // Create a new recipe box, logging into the given database.
  public RecipeBoxImp(String db_url, String user, String pass) {
    this.db = new MySQLDB(db_url, user, pass);
  }

  // TODO
  public void load() {

  }

  // Shut down the connection to the database.
  public void close() {
    this.db.close();
  }

  // Create a new recipe builder, signing ourselves on as the observer to notify upon its completion.
  public RecipeBuilder addRecipe() {
    return this.db.createRecipe(this);
  }

  // TODO
  public RecipeBuilder editRecipe(int r_id) {
    return null;
  }

  // TODO
  public DirectoryBuilder addDirectory() {
    return null;
  }

  // TODO
  public DirectoryBuilder editDirectory(int d_id) {
    return null;
  }

  // TODO
  public void deleteFile(int d_id) {

  }

  // TODO
  public void renameFile(int d_id, String newName) {

  }

  // TODO
  public TagBuilder addTag(String name) {
    return null;
  }

  // TODO
  public TagBuilder editTag(int t_id) {
    return null;
  }

  // TODO
  public void deleteTag(int t_id) {

  }

  // TODO
  public List<File> openDirectory(List<String> path) {
    return List.of();
  }

  // TODO
  public void moveFile(int f_id, List<String> newDir) {

  }

  // TODO
  public void selectTag(String name) {

  }

  // TODO
  public void deselectTag(String name) {

  }

  // TODO
  public void clearTags() {

  }

  // Receive a new recipe and potentially insert it into the working directory.
  public void notifyNew(int id, int parent, RecipeBuilder rec) {
    // If the new file was created in the working directory, insert it as such.
    if (parent == this.workingDir.getId()) {
      this.workingDir.addFile(rec.build(id));
    }
    /*
    If this file was created in a directory that is not loaded, we will discard it.
    */
  }

  // Receive a new directory and potentially insert it as a child of the working directory.
  public void notifyNew(int id, int parent, DirectoryBuilder dir) {
    // If the new file was created in the working directory, insert it as such.
    if (parent == this.workingDir.getId()) {
      this.workingDir.addFile(dir.build(id));
    }
    /*
    If this file was created in a directory that is not loaded, we don't need to load it.
    */
  }

  // TODO
  public void notifyMoved(int file, int newParent) {

  }

  // TODO
  public void notifyUpdated(File file) {

  }

  // TODO
  public void notifyNew(int id, TagBuilder tag) {

  }
}

import java.util.ArrayList;
import java.util.List;

import model.Directory;
import model.DirectoryBuilder;
import model.File;
import model.Recipe;
import model.RecipeBoxImp;
import model.RecipeBuilder;
import model.TagBuilder;

// Mock of RecipeBox to spy on observer behavior.
public class MockRecipeBox extends RecipeBoxImp {
  private StringBuilder log;
  private List<File> files;

  public MockRecipeBox(String db_url, String user, String pass) {
    super(db_url, user, pass);
    this.log = new StringBuilder();
    this.files = new ArrayList<>();
  }

  @Override
  public void notifyNew(int id, int parent, RecipeBuilder rec) {
    Recipe built = rec.build(id);
    this.log.append("New recipe ")
            .append(built.getName()).append(" ")
            .append("parent ").append(parent);
    this.files.add(built);
  }

  @Override
  public void notifyNew(int id, int parent, DirectoryBuilder dir) {
    Directory built = dir.build(id);
    this.log.append("New directory ")
            .append(built.getName()).append(" ")
            .append("parent ").append(parent);
    this.files.add(built);
  }

  @Override
  public void notifyMoved(int file, int newParent) {
    this.log.append("Moved ")
            .append(file).append(" ")
            .append("to ").append(newParent);
  }

  @Override
  public void notifyNew(int id, TagBuilder tag) {
    // todo
  }

  // Return the last-logged message(s) and clear the log.
  public String getLog() {
    String logState = this.log.toString();
    this.log = new StringBuilder();
    return logState;
  }

  public File getNewFile() {
    return this.files.getLast();
  }
}

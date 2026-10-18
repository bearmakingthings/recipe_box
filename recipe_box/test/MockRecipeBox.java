import java.util.List;

import model.DirectoryBuilder;
import model.File;
import model.RecipeBuilder;
import model.TagBuilder;

public class MockRecipeBox implements model.RecipeBox {

  public void load() {

  }

  public RecipeBuilder addRecipe() {
    return null;
  }

  public RecipeBuilder editRecipe(int r_id) {
    return null;
  }

  public DirectoryBuilder addDirectory() {
    return null;
  }

  public DirectoryBuilder editDirectory(int d_id) {
    return null;
  }

  public void deleteFile(int d_id) {

  }

  public void renameFile(int d_id, String newName) {

  }

  public TagBuilder addTag(String name) {
    return null;
  }

  public TagBuilder editTag(int t_id) {
    return null;
  }

  public void deleteTag(int t_id) {

  }

  public List<File> openDirectory(List<String> path) {
    return List.of();
  }

  public void moveFile(int f_id, List<String> newDir) {

  }

  public void selectTag(String name) {

  }

  public void deselectTag(String name) {

  }

  public void clearTags() {

  }
}

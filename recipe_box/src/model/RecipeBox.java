package model;

import java.util.List;

public interface RecipeBox {

  // Open the root directory and load all components.
  void load();

  // Creation, modification, and deletion operations on the recipes.
  Database.RecipeBuilder addRecipe();
  Database.RecipeBuilder editRecipe(int r_id);
  Database.DirectoryBuilder addDirectory();
  Database.DirectoryBuilder editDirectory(int d_id);
  void deleteFile(int d_id);
  void renameFile(int d_id, String newName);

  // Change the working directory.
  List<File> openDirectory(List<String> path); // todo should this signature be different?
  List<File> expandDirectory(List<String> path);
  void moveFile(int f_id, List<String> newDir);

  // Creation, modification, and deletion of tags.
  int addTag(String name);
  void deleteTag(int t_id);
  void renameTag(int t_id, String newName);

  // Selection and deselection of tags.
  void selectTag(String name);
  void deselectTag(String name);
  void clearTags();

}

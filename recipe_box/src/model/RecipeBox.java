package model;

import java.util.List;

public interface RecipeBox {

  // Open the root directory and load all components.
  void load();

  // Creation, modification, and deletion operations on the recipes.
  RecipeBuilder addRecipe();
  RecipeBuilder editRecipe(int r_id);
  DirectoryBuilder addDirectory();
  DirectoryBuilder editDirectory(int d_id);
  void deleteFile(int d_id);
  void renameFile(int d_id, String newName);

  // Creation, modification, and deletion of tags.
  TagBuilder addTag(String name);
  TagBuilder editTag(int t_id);
  void deleteTag(int t_id);

  // Change the working directory.
  List<File> openDirectory(List<String> path); // todo what should this signature be?
  void moveFile(int f_id, List<String> newDir);


  // Selection and deselection of tags.
  void selectTag(String name);
  void deselectTag(String name);
  void clearTags();

}

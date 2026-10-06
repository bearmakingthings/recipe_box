package model;

import java.util.Map;

// TODO NOTES:
// - database never exposes SQL syntax! nobody else should have to import java.sql!
//   - this also means all sqlexceptions must be in try/catches
// - REMOVE EDITOR INTERFACES AND JUST HAVE THEM BE IMPLEMENTOR CLASSES


// The database storing this recipe system.
interface Database {

  // Load a recipe or directory.
  Recipe openRecipe(int r_id) throws IllegalArgumentException;
  Directory openDirectory(int d_id) throws IllegalArgumentException;

  // Add or remove a tag from the selection.
  Map<Integer, File> filterOnTag(int t_id) throws IllegalArgumentException;
  Map<Integer, File> unfilterOnTag(int t_id) throws IllegalArgumentException;

  // Create new files or tags from a builder.
  Recipe createRecipe(RecipeBuilder r) throws RuntimeException;
  Directory createDirectory(DirectoryBuilder d) throws RuntimeException;
  Tag createTag(TagBuilder t) throws RuntimeException;

  // Edit files from a builder.
  void updateRecipe(int r_id, RecipeBuilder r) throws IllegalArgumentException;
  void updateDirectory(int d_id, DirectoryBuilder d) throws IllegalArgumentException;
  void updateTag(int t_id, TagBuilder t) throws IllegalArgumentException;

  // Delete files.
  void deleteFile(int f_id) throws IllegalArgumentException;
  void deleteTag(int t_id) throws IllegalArgumentException;

  // Apply or remove tags on files.
  void tagFile(int f_id, int t_id) throws IllegalArgumentException;
  void untagFile(int f_id, int t_id) throws IllegalArgumentException;



  // Create a new Recipe.
  interface RecipeBuilder {
    RecipeBuilder setName(String newName);
    RecipeBuilder setDoc(String document);
    RecipeBuilder setUrl(String url);
    RecipeBuilder setImg(String img);
    RecipeBuilder setParent(int d_id);
    RecipeBuilder submit() throws IllegalStateException;
  }

  // TODO remove
  // Edit an existing recipe, updating the associated Recipe object upon completion.
  interface RecipeEditor extends RecipeBuilder {
    RecipeEditor submit() throws IllegalStateException;
  }

  // Create a new Directory.
  interface DirectoryBuilder {
    DirectoryBuilder setName(String newName);
    DirectoryBuilder setParent(int d_id);
    DirectoryBuilder submit() throws IllegalStateException;
  }

  // TODO remove
  // Edit an existing directory, updating the associated Directory object upon submission.
  interface DirectoryEditor extends DirectoryBuilder {
    DirectoryEditor submit() throws IllegalStateException;
  }

  // Create a new Tag.
  interface TagBuilder {
    TagBuilder setName(String name);
    TagBuilder submit() throws IllegalStateException;
  }

  // TODO remove
  // Edit an existing tag, updating the associated Tag object upon submission.
  interface TagEditor extends TagBuilder {
    TagEditor submit() throws IllegalStateException;
  }

}

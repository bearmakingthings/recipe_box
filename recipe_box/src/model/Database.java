package model;

import java.util.List;

// TODO NOTES:
// - database never exposes SQL syntax! nobody else should have to import java.sql!
//   - this also means all sqlexceptions must be in try/catches
// - REMOVE EDITOR INTERFACES AND JUST HAVE THEM BE IMPLEMENTOR CLASSES


// The database storing this recipe system.
interface Database {

  // Load the details of a recipe.
  Recipe loadRecipe(int r_id) throws IllegalArgumentException;

  // Load the details of a directory. The Files it contains will be lazy-loaded, i.e. their
  // IDs will be loaded, but the according model objects will not.
  Directory loadDirectory(int d_id) throws IllegalArgumentException;

  // Populate the contents of the given directory.
  void loadDirectoryContents(Directory d) throws IllegalArgumentException;

  // Retrieve the IDs of the files in the selected directory matching the selected tags.
  List<Integer> filterFiles(int dir_id, List<Integer> tags) throws IllegalArgumentException;

  // Create new files or tags from a builder.
  Recipe createRecipe(RecipeBuilder r) throws RuntimeException;
  Directory createDirectory(DirectoryBuilder d) throws RuntimeException;
  Tag createTag(TagBuilder t) throws RuntimeException;

  // Edit the details of a file or tag from a builder.
  void updateRecipe(int r_id, RecipeBuilder r) throws IllegalArgumentException;
  void updateDirectory(int d_id, DirectoryBuilder d) throws IllegalArgumentException;
  void updateTag(int t_id, TagBuilder t) throws IllegalArgumentException;

  // Move a file.
  void moveFile(int f_id, int newParentDir) throws IllegalArgumentException;

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

package model;

import java.util.List;
import java.util.Map;

// Operations on the database, performed by other model objects.
interface Database {


  // Create a builder for a new file type or tag.
  RecipeBuilder createRecipe(FileObserver obs);
  DirectoryBuilder createDirectory(FileObserver obs);
  TagBuilder createTag(TagObserver obs);

  // Create a builder to edit a given file or tag.
  RecipeBuilder editRecipe(FileObserver obs, Recipe editing);
  DirectoryBuilder editDirectory(FileObserver obs, Directory editing);
  TagBuilder editTag(TagObserver obs, Tag editing);

  // Load the details of a recipe.
  Recipe loadRecipe(int r_id) throws RuntimeException;

  // Load the details of a directory, but not its contents.
  Directory loadDirectory(int d_id) throws RuntimeException;

  // Load the directories that are children of the given directory.
  Map<Integer, Directory> loadDirectoryChildren(int d_id) throws RuntimeException;

  // Load the non-directory contents of the given directory.
  Map<Integer, File> loadDirectoryContents(int d_id) throws RuntimeException;

  // Retrieve the IDs of the files in the selected directory matching the selected tags.
  List<Integer> filterFiles(int dir_id, List<Tag> tags) throws RuntimeException;

  // Move a file.
  void moveRecipe(int r_id, int newParent, FileObserver obs) throws RuntimeException;
  void moveDirectory(int d_id, int newParent, FileObserver obs) throws RuntimeException;

  // Delete files.
  void deleteFile(int file) throws RuntimeException;
  void deleteTag(int tag) throws RuntimeException;

  // Apply or remove tags on files.
  void tagFile(int file, int tag) throws RuntimeException;
  void untagFile(int file, int tag) throws RuntimeException;

  // Shut down the connection.
  void close() throws RuntimeException;
}

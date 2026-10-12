package model;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// The connection to the local recipe database and operations on it.
public class MySQLDB implements Database {
  // The active connection to the DB.
  private Connection con;

  public MySQLDB(String db_url, String user, String pass) throws RuntimeException {
    // Connect to the database.
    con = null;
    try {
      con = DriverManager.getConnection(
              "jdbc:mysql://" + db_url,
              user,
              pass);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
    if (con == null) {
      throw new RuntimeException("Connection successful, yet database not found.");
    }

    // Enable transactions. Necessary in order to insert an entry into the file table, then
    // retrieve the new file's id to insert the matching entry into the filetype-specific table.
    try {
      con.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  // Load the details of a recipe.
  public Recipe loadRecipe(int r_id) throws IllegalArgumentException {
    // Select the file from the recipes table.
    ResultSet recipeTuple;
    try {
      PreparedStatement getRecipe = con.prepareStatement("SELECT * FROM recipes WHERE id = ?");
      getRecipe.setInt(1, r_id);
      recipeTuple = getRecipe.executeQuery();
      // Check that the result set isn't empty.
      if (!recipeTuple.next()) {
        throw new RuntimeException("No recipe matching id " + r_id + " was found.");
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not load recipe " + r_id + ": " + e.getMessage());
    }

    // Initialize a new Recipe object from the results.
    try {
      String name = recipeTuple.getString("name");
      String doc = recipeTuple.getString("document");
      String img = recipeTuple.getString("image");
      String url = recipeTuple.getString("url");
      return new Recipe(r_id, name, doc, img, url);

    } catch (SQLException e) {
      throw new RuntimeException("Details for recipe " + r_id + " could not be retrieved: "
              + e.getMessage());
    }
  }

  // Load the details of a directory, but not its contents.
  public Directory loadDirectory(int d_id) throws IllegalArgumentException {
    // Select the file from the directories table.
    ResultSet dirTuple;
    try {
      PreparedStatement getDir = con.prepareStatement("SELECT * FROM directories WHERE id = ?");
      getDir.setInt(1, d_id);
      dirTuple = getDir.executeQuery();
      // Check that the result set isn't empty.
      if (!dirTuple.next()) {
        throw new RuntimeException("No directory matching id " + d_id + " was found.");
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not load directory " + d_id + ": " + e.getMessage());
    }

    // Initialize a new Directory object from the results.
    try {
      String name = dirTuple.getString("name");
      return new Directory(d_id, name);

    } catch (SQLException e) {
      throw new RuntimeException("Details for recipe " + d_id + " could not be retrieved: "
              + e.getMessage());
    }
  }

  // TODO
  public Map<Integer, File> loadDirectoryContents(int d_id) throws IllegalArgumentException {

    // Select the directories whose parent is this directory.
    ResultSet dirChildren;
    try {
      PreparedStatement getChildren = con.prepareStatement("SELECT * " +
              "FROM files " +
              "JOIN directories " +
              "ON files.id = directories.id " +
              "WHERE files.parent = ?");

      getChildren.setInt(1, d_id);
      dirChildren = getChildren.executeQuery();

    } catch (SQLException e) {
      throw new RuntimeException("Could not retrieve children in directory " + d_id +
              ": " + e.getMessage());
    }

    // Select the recipes whose parent is this directory.
    ResultSet dirContents;
    try {
      PreparedStatement getContents = con.prepareStatement("SELECT * " +
              "FROM files " +
              "JOIN recipes " +
              "ON files.id = recipes.id " +
              "WHERE files.parent = ?");

      getContents.setInt(1, d_id);
      dirContents = getContents.executeQuery();

    } catch (SQLException e) {
      throw new RuntimeException("Could not retrieve recipes in directory " + d_id +
              ": " + e.getMessage());
    }

    // Create a new object from each recipe and subdirectory and compile them into a map.
    Map<Integer, File> contents = new TreeMap<>();
    try {
      while (dirChildren.next()) {
        int childId = dirChildren.getInt("id");
        contents.put(childId, this.loadDirectory(childId));
      }
      while (dirContents.next()) {
        int contentId = dirContents.getInt("id");
        contents.put(contentId, this.loadRecipe(contentId));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not create Files for contents of directory " + d_id +
              ": " + e.getMessage());
    }

    return contents;
  }

  // TODO
  public List<Integer> filterFiles(int dir_id, List<Integer> tags) throws IllegalArgumentException {
    // Select the IDs of all files in the given directory matching all of the given tags.
    return List.of();
  }

  // TODO
  public Recipe createRecipe(RecipeBuilder r) throws RuntimeException {
    // Insert a new file with the given parent directory.
    // Retrieve the new file's id.
    // Insert a new recipe with the given id into the recipes table, using the details
    // in the builder.
    // Commit the transaction.
    return null;
  }

  // TODO
  public Directory createDirectory(DirectoryBuilder d) throws RuntimeException {
    // Insert a new file with the given parent directory.
    // Retrieve the new file's id.
    // Insert a new directory with the given id into the directory table, using the details
    // given in the builder.
    // Commit the transaction.
    return null;
  }

  // TODO
  public Tag createTag(TagBuilder t) throws RuntimeException {
    // Insert a new tag into the tag table with the details given in the builder.
    // Commit the statement.
    return null;
  }

  // TODO
  // TODO SHOULD YOU BE ABLE TO TAG DIRECTORIES? HM.
  /*
    arguments yes:
    - practical. e.g. you make a folder "recipes kara liked", kara is your celiac friend,
      you want to add this whole folder to your gluten-free tag, easier than individually tagging
      every recipe in folder.

    arguments no:
    - if you tag a folder and then tag recipes within the folder with the same tag, will untagging
      the folder also untag all the recipes?
    - well, that's probably a no. e.g. "recipes to try this spring" tag is initially put on a
      couple parfait recipes and a "desserts" folder, user later puts the recipes in that folder,
      later decides that it's overkill to tag the entire desserts folder for this spring. they
      untag folder, but probably want to keep those seasonal recipes tagged.
    - ok i think directories should be taggable.

    what about search?
    - if you filter on "xyz" from within a folder tagged xyz, but none of its contents are
      individually xyz-tagged, you still want to see all of them, right?
    - would require recursive search up the parent tree.
    - e.g. you're in folder path a -> b -> c -> d. you filter on "chicken", which b is tagged with.
    - what does select statement look like?
   */
  public void tagFile(int f_id, int t_id) throws IllegalArgumentException {
    // Create a new entry in the linking table.
    // If the operation fails on foreign key constraints, throw.
  }

  // TODO
  public void untagFile(int f_id, int t_id) throws IllegalArgumentException {
    // Remove the given id pair from the linking table.
  }

  public void close() throws RuntimeException {
    try {
      con.close();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  // TODO
  public void updateRecipe(int r_id, RecipeBuilder r) throws IllegalArgumentException {
    // Update the given recipe with the details in the builder.
    // If the operation fails, check the error message and throw an according exception.
  }

  // TODO
  public void updateDirectory(int d_id, DirectoryBuilder d) throws IllegalArgumentException {
    // Update the given directory with the details in the builder.
    // If the operation fails, check the error message and throw an according exception.
  }

  // TODO
  public void updateTag(int t_id, TagBuilder t) throws IllegalArgumentException {
    // Update the given tag with the details in the builder.
    // If the operation fails, check the error message and throw an according exception.
  }

  // TODO
  public void moveFile(int f_id, int newParentDir) throws IllegalArgumentException {
    // Update the file table entry for this file with the new parent directory.
  }

  // TODO
  public void deleteFile(int f_id) throws IllegalArgumentException {
    // Delete this file from the file table, removing its entry in the
    // filetype-specific table, as well as any of its entries in the tag linking table.
  }

  // TODO
  public void deleteTag(int t_id) throws IllegalArgumentException {
    // Delete this tag from the tag table, removing its entries in the linking table.
  }

  // TODO
  class MySQLRecipeBuilder implements RecipeBuilder {

    public RecipeBuilder setName(String newName) {
      return null;
    }

    public RecipeBuilder setDoc(String document) {
      return null;
    }

    public RecipeBuilder setUrl(String url) {
      return null;
    }

    public RecipeBuilder setImg(String img) {
      return null;
    }

    public RecipeBuilder setParent(int d_id) {
      return null;
    }

    public RecipeBuilder submit() throws IllegalStateException {
      return null;
    }
  }

  // TODO
  class MySQLDirectoryBuilder implements DirectoryBuilder {

    public DirectoryBuilder setName(String newName) {
      return null;
    }

    public DirectoryBuilder setParent(int d_id) {
      return null;
    }

    public DirectoryBuilder submit() throws IllegalStateException {
      return null;
    }
  }

  // TODO
  class MySQLTagBuilder implements TagBuilder {

    public TagBuilder setName(String name) {
      return null;
    }

    public TagBuilder submit() throws IllegalStateException {
      return null;
    }
  }

  // TODO
  class MySQLRecipeEditor extends MySQLRecipeBuilder {

  }

  // TODO
  class MySQLDirectoryEditor extends MySQLDirectoryBuilder {

  }

  // TODO
  class MySQLTagEditor extends MySQLTagBuilder {

  }

}

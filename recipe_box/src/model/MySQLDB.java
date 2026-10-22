package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.sql.Types.VARCHAR;

// The connection to the local recipe database and operations on it.
class MySQLDB implements Database {
  // The active connection to the DB.
  protected Connection con;

  // Log into the given database.
  MySQLDB(String db_url, String user, String pass) throws RuntimeException {
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

  // Close the connection to the database.
  public void close() throws RuntimeException {
    try {
      con.rollback();
      con.close();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  // Instantiate a new recipe builder and sign on an observer
  // to notify of its completion.
  public RecipeBuilder createRecipe(FileObserver obs) {
    return new MySQLRecipeBuilder(this, obs);
  }

  // Create a new Recipe object using the given builder.
  void submitNewRecipe(MySQLRecipeBuilder bdr) {

    MySQLFileBuilder fileBdr = bdr.fBdr;

    // Create a new file entry under the parent directory, and get its id.
    int recipeId = this.insertFile(fileBdr.parent);

    // Insert the recipe details into the recipes table.
    try {
      PreparedStatement recipeStmt = con.prepareStatement(
              "INSERT INTO recipes " +
              "(id, name, document, url, image) " +
              "VALUES (?, ?, ?, ?, ?);");

      // Set the retrieved file id as this recipe's id.
      recipeStmt.setInt(1, recipeId);
      // Set the name.
      recipeStmt.setString(2, fileBdr.name);

      // Add each optional feature, or NULL if it was not set.
      if (bdr.doc != null) {
        recipeStmt.setString(3, bdr.doc);
      }
      else {
        recipeStmt.setNull(3, VARCHAR);
      }
      if (bdr.url != null) {
        recipeStmt.setString(4, bdr.url);
      }
      else {
        recipeStmt.setNull(4, VARCHAR);
      }
      if (bdr.img != null) {
        recipeStmt.setString(5, bdr.img);
      }
      else {
        recipeStmt.setNull(5, VARCHAR);
      }

      // Execute the query.
      recipeStmt.executeUpdate();

      // Commit the transaction.
      con.commit();
    } catch (SQLException e) {
      throw new RuntimeException("Could not create new recipe: " + e.getMessage());
    }

    // Notify the file observer of the new recipe.
    fileBdr.obs.notifyNew(recipeId, fileBdr.parent, bdr);
  }

  public DirectoryBuilder createDirectory(FileObserver obs) {
    return new MySQLDirectoryBuilder(this, obs);
  }

  void submitNewDirectory(MySQLDirectoryBuilder bdr) {
    MySQLFileBuilder fileBdr = bdr.fBdr;
    // Insert a new file with the given parent directory.
    int dirId = this.insertFile(fileBdr.parent);

    // Insert a new directory with the given id and details into the directory table.
    try {
      PreparedStatement dirStmt = con.prepareStatement(
              "INSERT INTO directories " +
                      "(id, name) " +
                      "VALUES (?, ?);");

      // Set the retrieved file id as this directory's id.
      dirStmt.setInt(1, dirId);
      // Set its name.
      dirStmt.setString(2, fileBdr.name);

      // Execute the query.
      dirStmt.executeUpdate();

      // Commit the transaction.
      con.commit();
    } catch (SQLException e) {
      throw new RuntimeException("Could not create new directory: " + e.getMessage());
    }

    // Notify the observer.
    fileBdr.obs.notifyNew(dirId, fileBdr.parent, bdr);
  }
  
  // TODO CURRENT WORK BLOCK ----------------------------------------------------------------------------------------

  public TagBuilder createTag(TagObserver obs) {
    return new MySQLTagBuilder(this, obs);
  }

  void submitNewTag(MySQLTagBuilder bdr) {

  }

  // Create a builder to edit the given recipe.
  public RecipeBuilder editRecipe(FileObserver obs, Recipe editing) {
    return new MySQLRecipeEditor(this, obs, editing);
  }

  // Update the details of a recipe using the given editor.
  void submitRecipeEdits(MySQLRecipeEditor edr) {
    MySQLFileBuilder fileBdr = edr.fBdr;
    // If the recipe was moved, perform that change first.
    if (fileBdr.parent != null) {
      this.moveFile(edr.editing.id, fileBdr.parent);
    }

    // TODO
    // Update the recipe table entry with the changed fields.
    // Commit the change.

    // Notify the observer.
    fileBdr.obs.notifyUpdated(edr.editing);
  }

  // TODO
  public DirectoryBuilder editDirectory(FileObserver obs, Directory editing) {
    return null;
  }

  // TODO
  void submitDirectoryEdits(DirectoryBuilder edr) {

  }

  // TODO
  public TagBuilder editTag(TagObserver obs, Tag editing) {
    return null;
  }

  // TODO
  void submitTagEdits(TagBuilder edr) {
    // Insert a new tag into the tag table with the details given in the builder.
    // Commit the statement.
  }

  // TODO END CURRENT WORK BLOCK -------------------------------------------------------------------------

  // Load the details of a recipe.
  public Recipe loadRecipe(int r_id) throws RuntimeException {
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
      Recipe rec = new Recipe(r_id, recipeTuple.getString("name"));
      rec.document = recipeTuple.getString("document");
      rec.img = recipeTuple.getString("image");
      rec.url = recipeTuple.getString("url");
      return rec;

    } catch (SQLException e) {
      throw new RuntimeException("Details for recipe " + r_id + " could not be retrieved: "
              + e.getMessage());
    }
  }

  // Load the details of a directory, but not its contents.
  public Directory loadDirectory(int d_id) throws RuntimeException {
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

  // Select all the subdirectories of the given directory and load them into according objects.
  public Map<Integer, Directory> loadDirectoryChildren(int d_id) throws RuntimeException {
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
    // Create a new object from each subdirectory and compile them into a map.
    Map<Integer, Directory> children = new TreeMap<>();
    try {
      while (dirChildren.next()) {
        int childId = dirChildren.getInt("id");
        // If this directory is its own child, it is the root. Exclude it.
        // TODO maybe remove?
        if (childId == d_id) {
          continue;
        }
        children.put(childId, this.loadDirectory(childId));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not load subdirectories of " + d_id +
              ": " + e.getMessage());
    }

    return children;

  }

  // Select all the non-directory Files whose parent is the given directory and load them
  // into according objects.
  public Map<Integer, File> loadDirectoryContents(int d_id) throws RuntimeException {

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

    // Create a new object from each recipe and compile them into a map.
    Map<Integer, File> files = new TreeMap<>();
    try {
      while (dirContents.next()) {
        int contentId = dirContents.getInt("id");
        files.put(contentId, this.loadRecipe(contentId));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not load contents of directory " + d_id +
              ": " + e.getMessage());
    }
    return files;
  }

  // TODO
  public List<Integer> filterFiles(int dir_id, List<Tag> tags) throws RuntimeException {
    // Select the IDs of all files in the given directory matching all of the given tags.
    return List.of();
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
  public void tagFile(int file, int tag) throws RuntimeException {
    // Create a new entry in the linking table.
    // If the operation fails on foreign key constraints, throw.
  }

  // TODO
  public void untagFile(int file, int tag) throws RuntimeException {
    // Remove the given id pair from the linking table.
  }

  // TODO TEST
  // Move a file from its current directory to the one given.
  public void moveFile(int file, int newParent) throws RuntimeException {
    try {
      // Update the file table entry for this file with the new parent directory.
      PreparedStatement moveStmt = con.prepareStatement(
              "UPDATE files " +
                      "SET parent = ? " +
                      "WHERE id = ?;");
      moveStmt.setInt(1, newParent);
      moveStmt.setInt(2, file);
      if (moveStmt.executeUpdate() != 1) {
        throw new RuntimeException("File move was not executed.");
      }
      // Commit the change.
      con.commit();
    } catch (SQLException e) {
      throw new RuntimeException("Could not move file: " + e.getMessage());
    }

    // TODO notify the observer.
  }

  // TODO
  public void deleteFile(int file) throws RuntimeException {
    // Delete this file from the file table, removing its entry in the
    // filetype-specific table, as well as any of its entries in the tag linking table.
  }

  // TODO
  public void deleteTag(int tag) throws RuntimeException {
    // Delete this tag from the tag table, removing its entries in the linking table.
  }

  // Insert a new file into the database and return its ID. Does not commit.
  protected int insertFile(Integer parent) throws RuntimeException {
    if (parent == null) {
      throw new IllegalStateException("New file must have a parent directory.");
    }
    try {
      // Insert a new file with the given parent directory.
      PreparedStatement fileStmt = con.prepareStatement(
              "INSERT INTO files (parent) VALUES (?)");
      fileStmt.setInt(1, parent);
      if (fileStmt.executeUpdate() != 1) {
        throw new RuntimeException("File insertion was not executed..");
      }
      // Retrieve the newly inserted ID.
      PreparedStatement getFileId = con.prepareStatement("SELECT last_insert_id();");
      ResultSet idSet = getFileId.executeQuery();
      // Make sure the result set is not empty.
      if (!idSet.next()) {
        throw new RuntimeException("Could not retrieve inserted file's ID");
      }
      // The ID should be the first and only column in the result set.
      return idSet.getInt(1);

    } catch (SQLException e) {
      throw new RuntimeException("Error creating new file: " + e.getMessage());
    }
  }


}

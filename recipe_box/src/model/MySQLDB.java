package model;

import java.sql.*;
import java.util.List;

// The connection to the local recipe database.
class MySQLDB implements Database {
  // The active connection to the DB.
  private Connection db;

  public MySQLDB() throws RuntimeException {
    // Connect to the database.
    db = null;
    try {
      db = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/recipe_box",
              "recipe_program",
              "robot");
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
    if (db == null) {
      throw new RuntimeException("Connection successful, yet database not found.");
    }

    // TODO do we need transactions?
    // TODO need locking, for sure
    // Enable transactions.
    try {
      db.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  // TODO
  public Recipe loadRecipe(int r_id) throws IllegalArgumentException {
    // Select the ID from the file table, save its parent.
    // Select the ID from the recipe table.
    // Initialize a new Recipe object from them.
    return null;
  }

  // TODO
  public Directory loadDirectory(int d_id) throws IllegalArgumentException {
    // Select the ID from the file table, save its parent.
    // Select the ID from the directory table.
    // Initialize a new Directory object from them.
    return null;
  }

  // TODO
  public void loadDirectoryContents(Directory d) throws IllegalArgumentException {
    // Select all the directories whose parent is the given directory.
    // Lazy-load each of them into a Directory.
    // Insert the children directories into d's children.
    // Select all the recipes whose parent is d.
    // Load each of them into a Recipe.
    // Insert the recipes into d's contents.
  }

  // TODO
  public List<Integer> filterFiles(int dir_id, List<Integer> tags) throws IllegalArgumentException {
    // Select the IDs of all files in the given directory matching all of the given tags.
    return List.of();
  }

  // TODO
  public Recipe createRecipe(RecipeBuilder r) throws RuntimeException {
    // Get the lock on the file table.
    // Insert a new file with the given parent directory.
    // Retrieve the new file's id.
    // Release the lock on the table.
    // Insert a new recipe with the given id into the recipes table, using the details
    // in the builder.
    return null;
  }

  // TODO
  public Directory createDirectory(DirectoryBuilder d) throws RuntimeException {
    // Get the lock on the file table.
    // Insert a new file with the given parent directory.
    // Retrieve the new file's id.
    // Release the lock on the table.
    // Insert a new directory with the given id into the directory table, using the details
    // given in the builder.
    return null;
  }

  // TODO
  public Tag createTag(TagBuilder t) throws RuntimeException {
    // Insert a new tag into the tag table with the details given in the builder.
    return null;
  }

  // TODO
  // TODO SHOULD YOU BE ABLE TO TAG DIRECTORIES? HM.
  public void tagFile(int f_id, int t_id) throws IllegalArgumentException {
    // Create a new entry in the linking table.
    // If the operation fails on foreign key constraints, throw.
  }

  // TODO
  public void untagFile(int f_id, int t_id) throws IllegalArgumentException {
    // Remove the given id pair from the linking table.
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
    // Delete this file from the file table, cascading to its entry in the
    // recipe/directory tables.
  }

  // TODO
  public void deleteTag(int t_id) throws IllegalArgumentException {
    // Delete this tag from the tag table, cascading to its entries in the linking table.
  }

}

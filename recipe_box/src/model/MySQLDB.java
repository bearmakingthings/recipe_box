package model;

import java.sql.*;
import java.util.List;

// The connection to the local recipe database.
class MySQLDB implements Database {
  // The active connection to the DB.
  private Connection db;

  // The components of the last SELECT statement executed by this database.
  private int lastSelectedDir;
  private List<Integer> lastSelectedTags;


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

    // Enable transactions.
    try {
      db.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}

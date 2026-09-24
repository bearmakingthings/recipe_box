package model;

import java.sql.*;
import java.util.Dictionary;

// The connection to the local recipe database.
class MySQLDB {
  private Connection db;
  private Dictionary<Integer, String> changelog;

  public MySQLDB() {
    db = null;
    try {
      db = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/recipe_box",
              "recipe_program",
              "robot");
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't connect to database: " + e.getMessage());
    }
    if (db == null) {
      throw new RuntimeException("Connection successful, yet database not found.");
    }
  }

}

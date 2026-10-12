import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Database;
import model.Directory;
import model.MySQLDB;
import model.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests on the MySQLDB Database implementation.
/* TODO
    I actually don't know if I should be publicly exposing the database at all.
    I feel like I shouldn't be, since the only reason I've made it public was to
    test on it, and they say you shouldn't test anything that's not meant to be public.
    I can delete it later, I guess.
*/
public class MySQLDBTests {

  // The database to test on.
  private static Database db;

  // Log in to a test database on the local computer.
  @BeforeAll
  static void setUp() {
    db = new MySQLDB("localhost:3306/recipe_box_test",
            "recipe_program",
            "robot");
  }

  @AfterAll
  static void tearDown() {
    db.close();
  }

  // Load a recipe object and check the contents of its fields.
  @Test
  void testLoadRecipe() {
    // 2 is a test recipe I use, I'm cheating, I know.
    Recipe rec = db.loadRecipe(2);

    assertEquals(2, rec.getId());
    assertEquals("pie", rec.getName());
    assertEquals("pie.pdf", rec.getDocument());
    assertEquals("pie.com", rec.getUrl());
    assertEquals("pie.jpeg", rec.getImg());
  }

  // Load a directory object and check the contents of its fields.
  @Test
  void testLoadDirectory() {
    // 1 is the root.
    Directory dir = db.loadDirectory(1);

    assertEquals(1, dir.getId());
    assertEquals("home", dir.getName());
  }

}

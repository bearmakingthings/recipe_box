import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import model.Directory;
import model.File;
import model.MySQLDB;
import model.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  // Load the files in the root directory, check that they each are created with the correct data,
  // check that the root is excluded from its own children.
  @Test
  void testLoadDirectoryContents() {
    Map<Integer, File> contents = db.loadDirectoryContents(1);

    // Check that the results contain the recipe in the root.
    assertTrue(contents.containsKey(2));
    assertEquals(2, contents.get(2).getId());
    assertEquals("pie", contents.get(2).getName());
    // Check for the child directory.
    assertTrue(contents.containsKey(3));
    assertEquals(3, contents.get(3).getId());
    assertEquals("entrees", contents.get(3).getName());

    // Check that the results do not contain the root directory, which is its own parent.
    assertFalse(contents.containsKey(1));
  }

  // TESTS ON RECIPEBUILDER:

  // Create a recipe with all details given.
  @Test
  void testCreateFullRecipe() {
    // TODO working on rn
  }

  // Create a recipe with the essential details given, and
  // various combinations of the optional ones.

  // Attempt to create a recipe without a name.

  // Attempt to create a recipe without a parent directory.


  // TESTS ON RECIPEEDITOR:

  // Rename any individual string field of a recipe.

  // Nullify any individual string field of a recipe.

  // Move a recipe from the editor.

  // Change all fields of a recipe.

  // Attempt to edit a recipe with a non-recipe id.

  // Attempt to edit a recipe with a non-directory new parent.


}

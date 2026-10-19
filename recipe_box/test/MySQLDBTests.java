import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.RecipeBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Tests on the MySQLDB Database CRUD operations, through a RecipeBoxImp.
public class MySQLDBTests {

  private static MockRecipeBox rBox;
  private final int ROOT = 1;

  // Log in to a test database on the local computer.
  @BeforeAll
  static void setUp() {
    rBox = new MockRecipeBox("localhost:3306/recipe_box_test",
            "recipe_program",
            "robot");
  }

  @AfterAll
  static void tearDown() {
    rBox.close();
  }

  // TODO fixed database visibility, thus can no longer use these tests. how to check instead?
  //  could ask the recipebox to open a dir and retrieve a specific recipe, but still haven't
  //  decided how rbox communicates file info outside its package.
//  // Load a recipe object and check the contents of its fields.
//  @Test
//  void testLoadRecipe() {
//    // 2 is a test recipe I use, I'm cheating, I know.
//    Recipe rec = db.loadRecipe(2);
//
//    assertEquals(2, rec.getId());
//    assertEquals("pie", rec.getName());
//    assertEquals("pie.pdf", rec.getDocument());
//    assertEquals("pie.com", rec.getUrl());
//    assertEquals("pie.jpeg", rec.getImg());
//  }
//
//  // Load a directory object and check the contents of its fields.
//  @Test
//  void testLoadDirectory() {
//    // 1 is the root.
//    Directory dir = db.loadDirectory(1);
//
//    assertEquals(1, dir.getId());
//    assertEquals("home", dir.getName());
//  }
//
//  // Load the files in the root directory, check that they each are created with the correct data,
//  // check that the root is excluded from its own children.
//  @Test
//  void testLoadDirectoryContents() {
//    Map<Integer, File> contents = db.loadDirectoryContents(1);
//
//    // Check that the results contain the recipe in the root.
//    assertTrue(contents.containsKey(2));
//    assertEquals(2, contents.get(2).getId());
//    assertEquals("pie", contents.get(2).getName());
//    // Check for the child directory.
//    assertTrue(contents.containsKey(3));
//    assertEquals(3, contents.get(3).getId());
//    assertEquals("entrees", contents.get(3).getName());
//
//    // Check that the results do not contain the root directory, which is its own parent.
//    assertFalse(contents.containsKey(1));
//  }

  // TESTS ON RECIPEBUILDER:

  // Create a recipe with all details given.
  @Test
  void testCreateFullRecipe() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setParent(ROOT);
    bdr.setName("Salsa");
    bdr.setDoc("salsa.pdf");
    bdr.setImg("salsa.jpeg");
    bdr.setUrl("food.com/salsa");
    bdr.submit();

    assertEquals("New recipe Salsa parent " + String.valueOf(ROOT), rBox.getLog());
    // TODO how to check that all other fields are correct?
  }

  // Create a recipe with the essential details given, and
  // various combinations of the optional ones.
  @Test
  void testCreateRecipeEssential() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setParent(ROOT);
    bdr.setName("Salsa 2");
    bdr.submit();

    assertEquals("New recipe Salsa 2 parent " + String.valueOf(ROOT), rBox.getLog());
  }
  @Test
  void testCreateRecipeWithDoc() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setParent(ROOT);
    bdr.setName("Salsa 3");
    bdr.setDoc("salsa3.csv");
    bdr.submit();

    assertEquals("New recipe Salsa 3 parent " + String.valueOf(ROOT), rBox.getLog());
  }
  @Test
  void testCreateRecipeWithImg() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setParent(ROOT);
    bdr.setName("Salsa 4");
    bdr.setImg("salsa4.png");
    bdr.submit();

    assertEquals("New recipe Salsa 4 parent " + String.valueOf(ROOT), rBox.getLog());
  }
  @Test
  void testCreateRecipeWithUrl() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setParent(ROOT);
    bdr.setName("Salsa 5");
    bdr.setUrl("food.com/salsa5");
    bdr.submit();

    assertEquals("New recipe Salsa 5 parent " + String.valueOf(ROOT), rBox.getLog());
  }

  // Attempt to create a recipe without a name.
  @Test
  void testCreateUnnamedRecipe() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setParent(ROOT);
    RuntimeException except = assertThrows(RuntimeException.class, bdr::submit);
    assertEquals("A recipe must have a name.", except.getMessage());
  }

  // Attempt to create a recipe without a parent directory.
  @Test
  void testCreateNoParentRecipe() {
    RecipeBuilder bdr = rBox.addRecipe();
    bdr.setName("Bad salsa");
    RuntimeException except = assertThrows(RuntimeException.class, bdr::submit);
    assertEquals("A recipe must have a parent directory.", except.getMessage());
  }

  // TESTS ON RECIPEEDITOR:

  // Rename any individual string field of a recipe.

  // Nullify any individual string field of a recipe.

  // Move a recipe from the editor.

  // Change all fields of a recipe.

  // Attempt to edit a recipe with a non-recipe id.

  // Attempt to edit a recipe with a non-directory new parent.


}

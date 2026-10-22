package model;

// Edit an existing Recipe and update both the database and the object.
public class MySQLRecipeEditor extends MySQLRecipeBuilder {
  protected Recipe editing;

  MySQLRecipeEditor(MySQLDB db, FileObserver obs, Recipe editing) {
    super(db, obs);
    this.editing = editing;
  }

  @Override
  public void submit() throws RuntimeException {
    MySQLFileBuilder fileBdr = super.fBdr;
    // Check that the required fields have not been left empty.
    fileBdr.checkSubmitConditions();
    // Enter the changes into the database.
    fileBdr.db.submitRecipeEdits(this);

    // With the changes committed to the database, update the Recipe object.
    editing.name = fileBdr.name;
    editing.document = doc;
    editing.img = img;
    editing.url = url;
  }
}
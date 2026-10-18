package model;

// TODO TEST
public class MySQLRecipeEditor extends MySQLRecipeBuilder {
  protected Recipe editing;

  MySQLRecipeEditor(MySQLDB db, Recipe editing) {
    super(db, fObs);
    this.editing = editing;
    this.parent = null;
    this.name = null;
    this.doc = null;
    this.img = null;
    this.url = null;
  }

  @Override
  public void submit() throws RuntimeException {

    // Enter the changes into the database.
    this.db.submitRecipeEdits(this);

    // With the changes committed to the database, update the Recipe object.
    editing.name = name;
    editing.document = doc;
    editing.img = img;
    editing.url = url;
  }
}
package model;

// Build a new recipe and insert it into the database.
public class MySQLRecipeBuilder implements RecipeBuilder {
  MySQLFileBuilder fBdr;
  String doc;
  String url;
  String img;

  // Create a new builder and sign on its observer.
  MySQLRecipeBuilder(MySQLDB db, FileObserver obs) {
    this.fBdr = new MySQLFileBuilder(db, obs);
    this.doc = null;
    this.url = null;
    this.img = null;
  }

  public RecipeBuilder setName(String newName) {
    this.fBdr.setName(newName);
    return this;
  }

  public RecipeBuilder setDoc(String document) {
    this.doc = document;
    return this;
  }

  public RecipeBuilder setUrl(String url) {
    this.url = url;
    return this;
  }

  public RecipeBuilder setImg(String img) {
    this.img = img;
    return this;
  }

  public RecipeBuilder setParent(int d_id) {
    this.fBdr.setParent(d_id);
    return this;
  }

  // Submit this new recipe to be added to the database.
  public void submit() throws RuntimeException {
    // Check that the required fields are present before submitting.
    this.fBdr.checkSubmitConditions();
    this.fBdr.db.submitNewRecipe(this);
  }

  // Create a new Recipe object from the builder.
  public Recipe build(int id) throws IllegalStateException {
    Recipe thisRec = new Recipe(id, this.fBdr.name);
    thisRec.document = this.doc;
    thisRec.img = this.img;
    thisRec.url = this.url;
    return thisRec;
  }

}

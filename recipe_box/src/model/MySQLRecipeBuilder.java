package model;

// Build a new recipe and insert it into the database.
public class MySQLRecipeBuilder implements RecipeBuilder {
  MySQLDB db;
  FileObserver obs;
  Integer parent;
  String name;
  String doc;
  String url;
  String img;

  // Create a new builder and sign on its observer.
  MySQLRecipeBuilder(MySQLDB db, FileObserver obs) {
    this.db = db;
    this.obs = obs;
    this.name = null;
    this.doc = null;
    this.url = null;
    this.img = null;
    this.parent = null;
  }

  public RecipeBuilder setName(String newName) {
    this.name = newName;
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
    this.parent = d_id;
    return this;
  }

  // Submit this new recipe to be added to the database.
  public void submit() throws RuntimeException {
    // Check that the required fields are present before submitting.
    this.checkSubmitConditions();
    this.db.submitRecipe(this);
  }

  public Recipe build(int id) throws IllegalStateException {
    Recipe thisRec = new Recipe(id, this.name);
    thisRec.document = this.doc;
    thisRec.img = this.img;
    thisRec.url = this.url;
    return thisRec;
  }

  // Check that the recipe's name and parent are not null.
  protected void checkSubmitConditions() {
    if (this.name == null) {
      throw new IllegalStateException("A recipe must have a name.");
    }
    if (this.parent == null) {
      throw new IllegalStateException("A recipe must have a parent directory.");
    }
  }
}

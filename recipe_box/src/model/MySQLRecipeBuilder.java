package model;

// TODO MUST TEST
// Build a new recipe and insert it into the database.
public class MySQLRecipeBuilder implements RecipeBuilder {
  protected MySQLDB db;
  protected FileObserver obs;
  protected String name;
  protected String doc;
  protected String url;
  protected String img;
  protected Integer parent;

  // Create a new builder and sign on its observer.
  protected MySQLRecipeBuilder(MySQLDB db, FileObserver obs) {
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

  public void submit() throws RuntimeException {

    this.db.submitRecipe(this);
  }
}

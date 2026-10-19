package model;

// A recipe.
public class Recipe extends File {
  protected String document;
  protected String img;
  protected String url;

  Recipe(int id, String name) {
    super(id, name);
    this.document = null;
    this.img = null;
    this.url = null;
  }

  public String getDocument() {
    return this.document;
  }

  public String getImg() {
    return this.img;
  }

  public String getUrl() {
    return this.url;
  }

}

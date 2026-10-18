package model;

// A recipe.
public class Recipe extends File {
  protected String document;
  protected String img;
  protected String url;

  Recipe(int id, String name, String document, String img, String url) {
    super(id, name);
    this.document = document;
    this.img = img;
    this.url = url;
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

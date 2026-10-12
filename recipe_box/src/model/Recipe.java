package model;

// A recipe.
public class Recipe extends File {
  private String document;
  private String img;
  private String url;

  Recipe(int id, String name, String document, String img, String url) {
    super(id, name);
    this.document = document;
    this.img = img;
    this.url = url;
  }


  /* TODO MORE GETTERS MAY NEED TO BE DELETED,
      FIGURE THAT OUT.
      AGAIN, THOUGH, THE UIs MAY NEED THEM.
  */
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

package model;

public class RecipeEditor {
  Recipe editing;

  RecipeEditor(Recipe toEdit) {
    this.editing = toEdit;
    this.name = toEdit.name;
    this.document = toEdit.document;
    this.url = toEdit.url;
    this.img = toEdit.img;
    this.parent = toEdit.getParentId();
  }


  @Override
  public void submit() {
    this.editing.updateWith(this);
  }
}

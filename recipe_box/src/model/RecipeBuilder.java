package model;

// Add features to a new Recipe.
public interface RecipeBuilder {
  // Set the recipe's features.
  RecipeBuilder setName(String newName);
  RecipeBuilder setDoc(String document);
  RecipeBuilder setUrl(String url);
  RecipeBuilder setImg(String img);
  RecipeBuilder setParent(int d_id);

  // Insert this new recipe into the database.
  void submit() throws RuntimeException;
}
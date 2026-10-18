package model;

// Create a new Tag.
public interface TagBuilder {
  TagBuilder setName(String name);
  void submit() throws RuntimeException;
}

package model;

import java.util.Map;

public class TagSet {
  protected Database db;
  protected Map<Integer, Tag> selected;
  protected Map<Integer, Tag> unselected;

  TagSet(Database db) {
    this.db = db;
  }


  public void addTag(String name) {
    // todo
  }

  public void deleteTag(String name) {

  }

}

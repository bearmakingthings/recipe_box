package model;

public interface TagObserver {
  void notifyNew(int id, TagBuilder tag);
}

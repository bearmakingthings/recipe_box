package model;

// A recipe or directory.
abstract class Node {
  // The unique ID of this Node in the database.
  protected String id;
  // The title of this Node.
  protected String title;
  // The Node that comes before this one in the current directory. Null for the head of the list.
  protected Node prevElm;

}

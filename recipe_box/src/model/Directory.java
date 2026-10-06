package model;

import java.util.Map;

// A directory, which may contain any number of other Files.
public class Directory extends File {
  Map<Integer, Directory> children;
  Map<Integer, Recipe> contents;

}

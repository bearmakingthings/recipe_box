package model;

public class RecipeBoxImp {

  // The set of all tags.
  private TagSet tags;

  // The root directory.
  private Directory root;

  // The working directory.
  private Directory workingDir;

  // TODO NOTES:
  // - the contents of workingDir may not always be equal to the fileset being displayed,
  //   but the viewed files are always a subset of the working dir, so it makes the most sense
  //   to store the working directory.
  // - good god that took me forever.

}

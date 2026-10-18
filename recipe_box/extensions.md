
## Model

### Database
- Maybe make separate File types for recipes with locally stored documents vs web links?
  - For that reason, keep loadDirectoryContents returning a map<integer, file>

## View

### GUI
- Probably will want GUI running in a separate thread from model, so it can show loading screens and the like.
- Eventually, add a checkbox selection menu for bulk delete/move actions.
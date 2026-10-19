USE recipe_box_test;

-- Clear all files but the root directory.
DELETE FROM files WHERE id > 1;
-- Clear all tags.
DELETE FROM tags WHERE id > 0;

SELECT * FROM files;
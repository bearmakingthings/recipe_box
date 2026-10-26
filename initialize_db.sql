-- Creates the recipe database.
DROP DATABASE IF EXISTS recipe_box;
CREATE DATABASE recipe_box;
USE recipe_box;

-- Directories and their names.
CREATE TABLE directories
(
	id		INT				PRIMARY KEY AUTO_INCREMENT,
    name 	VARCHAR(100)	NOT NULL,
    parent 	INT 			NOT NULL,	
    CONSTRAINT dir_parent 
		FOREIGN KEY (parent) REFERENCES directories (id)
);

-- Recipes and their details.
CREATE TABLE recipes
(
	id			INT				PRIMARY KEY AUTO_INCREMENT,
    name		VARCHAR(100)	NOT NULL,
    document	VARCHAR(20),
    url			VARCHAR(150),
    image		VARCHAR(20),
    parent 		INT 	NOT NULL,
    CONSTRAINT rec_parent 
		FOREIGN KEY (parent) REFERENCES directories (id)
);

-- Add the root directory.
INSERT INTO directories (name, parent) VALUES ("root", 1);

-- Tags and their names.
CREATE TABLE tags
(
	id		INT 			PRIMARY KEY AUTO_INCREMENT,
    name	VARCHAR(100)	UNIQUE NOT NULL
);

-- Linking table between tags and the recipes they contain.
CREATE TABLE recipe_tagged
(
	tag_id			INT 	REFERENCES tags (id) ON DELETE CASCADE,
    recipe_id		INT		REFERENCES recipes (id) ON DELETE CASCADE,
    CONSTRAINT tag_pk
		PRIMARY KEY (tag_id, recipe_id)
);

-- TODO ADD DIRECTORY TAG LINKS?

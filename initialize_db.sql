-- Creates the recipe database.
CREATE DATABASE IF NOT EXISTS recipe_box;
USE recipe_box;

-- All recipes and directories in the database.
CREATE TABLE IF NOT EXISTS files
(
	id		INT		PRIMARY KEY AUTO_INCREMENT,
    parent 	INT		NOT NULL
);

-- Add the root directory.
INSERT INTO files VALUES (DEFAULT, 1);

-- Recipes and their details.
CREATE TABLE recipes
(
	id				INT				PRIMARY KEY,
    name			VARCHAR(100)	NOT NULL,
    document		VARCHAR(20),
    url				VARCHAR(150),
    image			VARCHAR(20),
    CONSTRAINT r_id FOREIGN KEY (id) REFERENCES files (id)
);

-- Directories and their names.
CREATE TABLE directories
(
	id				INT				PRIMARY KEY,
    name			VARCHAR(100)	NOT NULL,
    CONSTRAINT d_id 
		FOREIGN KEY (id) REFERENCES files (id)
);

-- Add the root directory before the foreign key constraint is placed on the directory table.
-- (Note that without this initial entry, the constraints on the directories and files 
-- tables would cause deadlock when trying to insert into either.)
INSERT INTO directories VALUES (1, "home");

-- Update the file table so that the "parent" column must refer to a directory in the directory table.
ALTER TABLE files 
ADD CONSTRAINT parent_dir
	FOREIGN KEY (parent) REFERENCES directories (id);

-- Tags and their names.
CREATE TABLE tags
(
	id		INT 			PRIMARY KEY AUTO_INCREMENT,
    name	VARCHAR(100)	NOT NULL
);

-- Linking table between tags and the recipes they contain.
CREATE TABLE tag_contents 
(
	tag_id		INT 	REFERENCES tags (id),
    file_id		INT		REFERENCES files (id),
    CONSTRAINT tag_pk
		PRIMARY KEY (tag_id, file_id)
);

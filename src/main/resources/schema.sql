CREATE TABLE movie (
    id VARCHAR PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    summary VARCHAR(250) NOT NULL
);

CREATE TABLE contributor_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE contributor (
    id VARCHAR PRIMARY KEY,
    last_name VARCHAR(250) NOT NULL,
    first_name VARCHAR(250) NOT NULL,
    contributor_type_id INT NOT NULL,
    CONSTRAINT fk_contributor_type_id FOREIGN KEY (contributor_type_id)
    REFERENCES contributor_type(id)
);


INSERT INTO contributor_type (name)
VALUES ('Actor'),
        ('Director'),
        ('Producer'),
        ('Writer'),
        ('Editor'),
        ('Music Composer'),
        ('Costume Designer');
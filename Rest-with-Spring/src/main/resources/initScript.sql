CREATE TABLE author (
                        id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255)
);

CREATE TABLE book_genre (
                            book_id INT,
                            genre_id INT,
                            PRIMARY KEY (book_id, genre_id),
                            FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                            FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);
CREATE TABLE book
(
    id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255),
    price     INT,
    author_id INT,
    CONSTRAINT author_id_key FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

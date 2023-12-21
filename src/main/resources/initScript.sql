CREATE TABLE author (
                        id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255)
);

CREATE TABLE book
(
    id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255),
    price     INT,
    author_id INT,
    CONSTRAINT author_id_key FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

CREATE TABLE genre
(
    id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255)
);



CREATE TABLE book_genre (
                            book_id INT,
                            genre_id INT,
                            PRIMARY KEY (book_id, genre_id),
                            FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                            FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

INSERT INTO author (name) values
                              ('Channing Gordon'),
                              ('Oprah Nguyen'),
                              ('Lucius Livingston'),
                              ('Jenette Wolfe'),
                              ('Jemima Rivas'),
                              ('Samuel Robertson'),
                              ('Marvin Taylor'),
                              ('Lee Underwood'),
                              ('Marvin Rocha'),
                              ('Winter French'),
                              ('Yvonne Coffey'),
                              ('Tucker Brewer'),
                              ('Buckminster Rocha'),
                              ('Glenna Myers'),
                              ('Christopher Morin'),
                              ('Wynter Stephens'),
                              ('Selma Lamb'),
                              ('Zelenia Conley'),
                              ('Lacey Nunez'),
                              ('Serena Cannon');

INSERT INTO book (name, price, author_id) VALUES
                                              ('Oleg Fuller',607,12),
                                              ('Quintessa Kelley',1709,3),
                                              ('Hilel Rhodes',1546,5),
                                              ('Mary Curry',718,17),
                                              ('Julian Faulkner',1846,15),
                                              ('Vaughan Holder',732,5),
                                              ('Ali Meyer',745,8),
                                              ('Basil Clayton',565,19),
                                              ('Craig Nguyen',791,14),
                                              ('Darius Holder',550,15),
                                              ('Mohammad Estes',1018,7),
                                              ('Ina Blackburn',1638,16),
                                              ('Eleanor Mullins',951,8),
                                              ('Vernon Sandoval',1899,11),
                                              ('Kim James',521,12),
                                              ('Hector Osborne',884,13),
                                              ('Cathleen Rowe',736,18),
                                              ('Roanna Craft',872,18),
                                              ('Seth Wheeler',490,16),
                                              ('Zeph Tillman',540,13),
                                              ('Shannon Rosa',1888,13),
                                              ('Roanna Garrison',1506,7),
                                              ('Alana Walls',1789,11),
                                              ('Keane Roberson',695,8),
                                              ('Hunter Jennings',1925,7),
                                              ('Bo Weiss',1297,14),
                                              ('Xenos Baldwin',481,18),
                                              ('Jason Hudson',1823,7),
                                              ('Karleigh Landry',1220,19),
                                              ('Vincent Stark',1539,13),
                                              ('Boris Hahn',1823,16),
                                              ('Renee Fulton',584,16),
                                              ('Mia Spence',1672,13),
                                              ('Angelica Sampson',1520,5),
                                              ('Melissa Harris',961,4),
                                              ('Keaton Irwin',1849,4),
                                              ('Athena Rocha',1028,16),
                                              ('Quentin Mcconnell',1631,3),
                                              ('Florence Hardin',1307,14),
                                              ('Paula Mcintyre',1143,3),
                                              ('Ruby Hooper',733,6),
                                              ('Sandra Steele',820,14),
                                              ('Amos Kidd',564,17),
                                              ('Ainsley Beasley',518,4),
                                              ('Amy Davis',1365,3),
                                              ('Nigel Drake',1882,2),
                                              ('Vielka Camacho',1631,14),
                                              ('Buckminster Garrett',1012,10),
                                              ('Dana Mooney',409,15),
                                              ('Rafael Hardin',941,8),
                                              ('Clarke Rios',466,11),
                                              ('Mona Arnold',1418,17),
                                              ('Zoe O brien',1301,9),
                                              ('Samantha Hughes',1437,5),
                                              ('Castor Gallagher',1574,16),
                                              ('Aristotle Hampton',1433,16),
                                              ('Cara Mcclure',1934,19),
                                              ('Keely Clements',1611,20),
                                              ('Ima Hancock',699,9),
                                              ('Carter Jordan',1505,3),
                                              ('Travis Velasquez',1423,10),
                                              ('Silas Head',997,17),
                                              ('Dahlia Sanford',1564,19),
                                              ('Rajah Odom',1958,10),
                                              ('Ori Owens',1168,12),
                                              ('Frances Morse',1765,8),
                                              ('Channing Hurley',734,8),
                                              ('Akeem Sosa',456,18),
                                              ('Rose Vincent',1459,9),
                                              ('Daniel Dorsey',1689,12),
                                              ('Sylvia Gamble',1539,20),
                                              ('Carly Simon',888,6),
                                              ('Katell Collier',1258,14),
                                              ('Octavius Stafford',858,6),
                                              ('Nasim Jimenez',1077,9),
                                              ('Rahim Mooney',1296,9),
                                              ('Gregory Banks',592,19),
                                              ('Burton Stephenson',1555,7),
                                              ('Xanthus Glass',1550,2),
                                              ('David Castaneda',1288,18),
                                              ('Desiree Gentry',454,14),
                                              ('Alea Pena',1930,2),
                                              ('Jaime Alexander',495,4),
                                              ('Savannah Bennett',560,6),
                                              ('Michelle Vang',420,1),
                                              ('Vanna Hogan',1491,19),
                                              ('Orson Welch',589,13),
                                              ('Deacon Collins',1816,19),
                                              ('Raja Strickland',521,1),
                                              ('Kevyn Roth',1722,14),
                                              ('Declan Cabrera',1664,13),
                                              ('Joy Nunez',1651,6),
                                              ('Brenden Morgan',1874,17),
                                              ('Camilla Chandler',869,2),
                                              ('Elvis Jensen',1475,17),
                                              ('Xanthus Mcneil',813,3),
                                              ('Eve Casey',1176,18),
                                              ('Isabella Cannon',610,2),
                                              ('Allen Kinney',1880,11),
                                              ('Knox Bentley',1681,18);

INSERT INTO genre (name) values
                             ('Detective'),
                             ('Crime'),
                             ('Science'),
                             ('Post-apocalyptic'),
                             ('Fantasy'),
                             ('Western'),
                             ('Horror'),
                             ('Classic'),
                             ('Humor'),
                             ('Folklore');

INSERT INTO book_genre (book_id, genre_id) VALUES
                                               (1,10),
                                               (2,8),
                                               (3,10),
                                               (4,7),
                                               (5,9),
                                               (6,9),
                                               (7,3),
                                               (8,2),
                                               (9,10),
                                               (10,10),
                                               (11,8),
                                               (12,8),
                                               (13,9),
                                               (14,8),
                                               (15,9),
                                               (16,9),
                                               (17,7),
                                               (18,3),
                                               (19,8),
                                               (20,9),
                                               (21,3),
                                               (22,5),
                                               (23,1),
                                               (24,3),
                                               (25,5),
                                               (26,7),
                                               (27,8),
                                               (28,2),
                                               (29,5),
                                               (30,5),
                                               (31,1),
                                               (32,2),
                                               (33,5),
                                               (34,4),
                                               (35,8),
                                               (36,5),
                                               (37,10),
                                               (38,2),
                                               (39,10),
                                               (40,4),
                                               (41,9),
                                               (42,2),
                                               (43,1),
                                               (44,5),
                                               (45,7),
                                               (46,8),
                                               (47,5),
                                               (48,5),
                                               (49,7),
                                               (50,5),
                                               (51,7),
                                               (52,4),
                                               (53,4),
                                               (54,4),
                                               (55,2),
                                               (56,4),
                                               (57,4),
                                               (58,6),
                                               (59,3),
                                               (60,7),
                                               (61,8),
                                               (62,2),
                                               (63,7),
                                               (64,7),
                                               (65,4),
                                               (66,10),
                                               (67,5),
                                               (68,5),
                                               (69,6),
                                               (70,8),
                                               (71,2),
                                               (72,1),
                                               (73,9),
                                               (74,8),
                                               (75,6),
                                               (76,9),
                                               (77,2),
                                               (78,9),
                                               (79,8),
                                               (80,5),
                                               (81,9),
                                               (82,7),
                                               (83,2),
                                               (84,7),
                                               (85,8),
                                               (86,1),
                                               (87,2),
                                               (88,10),
                                               (89,10),
                                               (90,1),
                                               (91,3),
                                               (92,4),
                                               (93,1),
                                               (94,2),
                                               (95,10),
                                               (96,7),
                                               (97,6),
                                               (98,3),
                                               (99,5),
                                               (100,7),
                                               (1,4),
                                               (2,5),
                                               (3,9),
                                               (4,4),
                                               (5,6),
                                               (6,2),
                                               (7,8),
                                               (9,3),
                                               (10,9),
                                               (11,6),
                                               (12,10),
                                               (13,2),
                                               (14,2),
                                               (16,3),
                                               (17,10),
                                               (18,7),
                                               (21,5),
                                               (22,3),
                                               (23,7),
                                               (24,1),
                                               (27,4),
                                               (28,10),
                                               (29,6),
                                               (30,9),
                                               (31,9),
                                               (32,7),
                                               (33,4),
                                               (34,8),
                                               (35,1),
                                               (37,8),
                                               (38,9),
                                               (39,2),
                                               (42,3),
                                               (43,10),
                                               (45,8),
                                               (46,9),
                                               (47,1),
                                               (48,10),
                                               (49,6),
                                               (50,8),
                                               (51,9),
                                               (52,6),
                                               (53,1),
                                               (56,8),
                                               (57,6),
                                               (58,7),
                                               (59,7),
                                               (60,4),
                                               (61,2),
                                               (63,5),
                                               (64,1),
                                               (65,1),
                                               (66,7),
                                               (67,10),
                                               (68,7),
                                               (69,1),
                                               (70,7),
                                               (71,6),
                                               (72,3),
                                               (73,7),
                                               (74,4),
                                               (76,4),
                                               (77,3),
                                               (78,1),
                                               (79,10),
                                               (80,6),
                                               (81,10),
                                               (82,9),
                                               (83,3),
                                               (84,8),
                                               (85,10),
                                               (86,9),
                                               (87,7),
                                               (88,8),
                                               (89,5),
                                               (90,7),
                                               (91,4),
                                               (92,10),
                                               (93,7),
                                               (94,1),
                                               (96,10),
                                               (97,9),
                                               (98,4),
                                               (99,1),
                                               (100,2),
                                               (1,3),
                                               (2,10),
                                               (3,4),
                                               (4,3),
                                               (5,10),
                                               (6,6),
                                               (7,5),
                                               (10,8),
                                               (11,5),
                                               (12,4),
                                               (13,5),
                                               (15,7),
                                               (16,5),
                                               (17,5),
                                               (19,3),
                                               (20,8),
                                               (21,2),
                                               (23,3),
                                               (24,10),
                                               (25,2),
                                               (26,5),
                                               (27,10),
                                               (28,4),
                                               (29,8),
                                               (31,3),
                                               (32,1),
                                               (33,8),
                                               (35,2),
                                               (36,1),
                                               (37,1),
                                               (38,3),
                                               (39,6),
                                               (40,10),
                                               (43,3),
                                               (44,2),
                                               (45,10),
                                               (46,4),
                                               (47,2),
                                               (49,4),
                                               (50,10),
                                               (51,1),
                                               (52,7),
                                               (53,6),
                                               (54,6),
                                               (55,3),
                                               (57,8),
                                               (59,10),
                                               (60,5),
                                               (61,7),
                                               (62,9),
                                               (65,7),
                                               (66,4),
                                               (67,7),
                                               (68,6),
                                               (69,3),
                                               (70,1),
                                               (71,1),
                                               (72,2),
                                               (73,3),
                                               (74,2),
                                               (75,10),
                                               (76,1),
                                               (84,3),
                                               (86,4),
                                               (87,1),
                                               (89,4),
                                               (90,8),
                                               (91,1),
                                               (92,6),
                                               (93,10),
                                               (94,6),
                                               (96,2),
                                               (97,2),
                                               (98,6),
                                               (99,6);



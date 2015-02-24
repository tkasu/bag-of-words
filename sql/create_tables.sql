CREATE TABLE release_info
(
id SERIAL PRIMARY KEY,
company_name VARCHAR(255) NOT NULL,
company_ticker VARCHAR(255),
publishing_year VARCHAR(255),
publish_date VARCHAR(255)
);

CREATE TABLE release_text
(
release_id int,
order_nro int,
word VARCHAR(255),
PRIMARY KEY (release_id, order_nro),
FOREIGN KEY (release_id) REFERENCES release_info(id)
);

CREATE TABLE dictionary
(
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL
);

CREATE TABLE dict_group
(
id SERIAL PRIMARY KEY,
head_word VARCHAR(255) NOT NULL
);

CREATE TABLE dict_words
(
dict_id SERIAL,
word VARCHAR(255),
group_id int,
PRIMARY KEY (dict_id, word),
FOREIGN KEY (dict_id) REFERENCES dictionary(id),
FOREIGN KEY (group_id) REFERENCES dict_group(id)
);

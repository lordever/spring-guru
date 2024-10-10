CREATE TABLE if NOT EXISTS beer (
    id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    style varchar(255),
    upc varchar(255),
    quantity integer,
    price decimal,
    created_date timestamp,
    last_modified_date timestamp
);

CREATE TABLE if NOT EXISTS customer (
    id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    version integer,
    created_date timestamp,
    last_modified_date timestamp
);
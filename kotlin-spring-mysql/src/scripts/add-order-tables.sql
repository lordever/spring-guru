drop table if exists beer;
drop table if exists customer;
drop table if exists beer_order_line;
drop table if exists beer_order;

create table beer
(
    price       decimal(38, 2) not null,
    quantity    integer,
    style       tinyint        not null check (style between 0 and 9),
    version     integer,
    create_date datetime(6),
    update_date datetime(6),
    id          varchar(36)    not null,
    name        varchar(50)    not null,
    upc         varchar(255)   not null,
    primary key (id)
) engine = InnoDB;

create table customer
(
    version            integer,
    created_date       datetime(6),
    last_modified_date datetime(6),
    id                 varchar(36) not null,
    name               varchar(255),
    primary key (id)
) engine = InnoDB;

CREATE TABLE 'beer_order'
(
    id                 varchar(36) NOT NULL,
    created_date       datetime(6)  DEFAULT NULL,
    customer_ref       varchar(255) DEFAULT NULL,
    last_modified_date datetime(6)  DEFAULT NULL,
    version            bigint       DEFAULT NULL,
    customer_id        varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;

CREATE TABLE 'beer_order_line'
(
    id                 varchar(36) NOT NULL,
    beer_id            varchar(36) DEFAULT NULL,
    create_date        datetime(6) DEFAULT NULL,
    last_modified_date datetime(6) DEFAULT NULL,
    order_quantity     int         DEFAULT NULL,
    quantity_allocated int         DEFAULT NULL,
    version            bigint      DEFAULT NULL,
    beer_order_id      varchar(36) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id),
    CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id)
) ENGINE = InnoDB

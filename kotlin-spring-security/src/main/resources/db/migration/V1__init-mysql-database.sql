drop table if exists beer;

drop table if exists customer;


create table beer
(
    id          varchar(36)    not null,
    name        varchar(50)    not null,
    style tinyint not null check (style between 0 and 9),
    create_date datetime(6),
    price       decimal(38, 2) not null,
    quantity    integer,
    upc         varchar(255)   not null,
    update_date datetime(6),
    version     integer,
    primary key (id)
) engine = InnoDB;

create table customer
(
    id           varchar(36) not null,
    created_date datetime(6),
    name         varchar(255),
    last_modified_date  datetime(6),
    version      integer,
    primary key (id)
) engine = InnoDB;

drop table if exists beer;

drop table if exists customer;

create table beer (
                      price decimal(38,2) not null,
                      quantity integer,
                      style tinyint not null check (style between 0 and 9),
                      version integer,
                      create_date datetime(6),
                      update_date datetime(6),
                      id varchar(36) not null,
                      name varchar(50) not null,
                      upc varchar(255) not null,
                      primary key (id)
) engine=InnoDB;

create table customer (
                          version integer,
                          created_date datetime(6),
                          last_modified_date datetime(6),
                          id varchar(36) not null,
                          name varchar(255),
                          primary key (id)
) engine=InnoDB;

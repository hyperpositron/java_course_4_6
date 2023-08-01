create table driver
(
    id serial primary key,
    name varchar(50) not null,
    age int not null default 18,
    has_licence boolean,
    car_id int references cars(id)
);

create table cars
(
    id serial primary key,
    manufecturer varchar(50) not null,
    model varchar(50) not null,
    price numeric(11, 2)
);
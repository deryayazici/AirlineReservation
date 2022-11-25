BEGIN TRANSACTION;

DROP TABLE IF EXISTS reservation,airport,flight,airplane, passenger, airline;


CREATE TABLE airline
(
    airline_id serial,
    airline_name varchar(50) not null,

    constraint pk_airline primary key (airline_id)
);

CREATE TABLE airplane
(
    airplane_id serial,
    plane_name varchar(50) not null,
    first_class_seats int not null,
	first_class_price decimal not null,
    business_class_seats int not null,
	business_class_price decimal not null,
    economy_class_seats int not null,
	economy_class_price decimal not null,
    airline_id int not null,

    constraint pk_airplane primary key (airplane_id),
    constraint fk_airplane_airline foreign key (airline_id) references airline (airline_id)
);
CREATE TABLE passenger
(
	passenger_id serial not null,
    email varchar(50) UNIQUE not null,
    last_name varchar(50) not null,
    first_name varchar(50) not null,
    phone_number varchar(11) not null,

    constraint pk_passenger primary key (passenger_id)

);
CREATE TABLE airport
(
  airport_id Serial not null,
  airport_name varchar(50) not null,
  city_name varchar(50) not null,
  country_name varchar(50) not null,

  constraint pk_airport primary key (airport_id)

);

CREATE TABLE flight
(
    flight_id serial not null,
    airplane_id int not null,
    departure_date date not null,
    arrival_date date not null,
    departure_airport_name varchar(50) not null,
    arrival_airport_name   varchar(50) not null,
	departure_time time not null,
	arrival_time time not null,
	departure_airport_id int not null,
	arrival_airport_id int not null,

    constraint pk_flight primary key (flight_id),
    constraint fk_flight_airplane foreign key (airplane_id) references airplane (airplane_id),
	constraint fk_flight_departure_airport foreign key (departure_airport_id) references airport (airport_id),
	constraint fk_flight_arrival_airport foreign key (arrival_airport_id) references airport (airport_id)

);
CREATE TABLE reservation
(
   reservation_id serial not null,
   flight_id int not null,
   email varchar(50) not null,
   type_of_seats varchar(20) not null,
   number_of_seats int not null,

   constraint pk_reservation primary key (reservation_id),
   constraint fk_reservation_passenger foreign key (email) references passenger (email),
   constraint fk_reservation_flight foreign key (flight_id) references flight (flight_id)
);

COMMIT;

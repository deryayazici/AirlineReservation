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
    plane_type varchar(50) not null,
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

INSERT INTO airline (airline_name)
VALUES ('Turkish Airlines');

Insert Into airplane (plane_type,first_class_seats,first_class_price,business_class_seats, business_class_price,economy_class_seats,economy_class_price,airline_id)
Values ('Boeing 747',6,3000,26,2000,314,1200,1);

Insert Into airplane (plane_type,first_class_seats,first_class_price,business_class_seats, business_class_price,economy_class_seats,economy_class_price,airline_id)
Values ('Boeing 787',0,0,48,2000,146,800,1);

Insert Into airplane (plane_type,first_class_seats,first_class_price,business_class_seats, business_class_price,economy_class_seats,economy_class_price,airline_id)
Values ('Airbus A350',0,0,40,2100,263,850,1);

Insert Into airport (airport_name, city_name, country_name)
Values ('Istanbul Airport', 'Istanbul','Turkey');

Insert Into airport (airport_name, city_name, country_name)
Values ('Dulles International Airport', 'Washington DC','USA');

Insert into flight (airplane_id,departure_date,arrival_date,departure_airport_name,arrival_airport_name,departure_time,arrival_time,departure_airport_id,arrival_airport_id)
Values (1,'01/15/2023','01/15/2023','Istanbul Airport','Dulles International Airport','16:00','20:00',1,2)



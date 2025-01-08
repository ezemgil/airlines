-- This file contains the DDL statements for creating the database schema.
-- Database schema generated by dbdiagram.io defined on data/model/erd.dbml
-- PostgreSQL version: 17

CREATE TABLE genders (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE continents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    code CHAR(3) UNIQUE NOT NULL
);

CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code CHAR(2),
    continent_id INTEGER,
    UNIQUE (name, code)
);

CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    country_id INTEGER,
    UNIQUE (name, country_id)
);

CREATE TABLE passengers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender_id SMALLINT,
    birth_date DATE NOT NULL,
    country_id INTEGER NOT NULL,
    CHECK (birth_date <= current_date)
);

CREATE TABLE airports (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city_id INTEGER NOT NULL,
    iata CHAR(3) UNIQUE,
    icao CHAR(4) UNIQUE,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    altitude REAL NOT NULL,
    utc REAL,
    dst_offset_id SMALLINT,
    timezone VARCHAR(50),
    CHECK (latitude BETWEEN -90 AND 90),
    CHECK (longitude BETWEEN -180 AND 180)
);

CREATE TABLE dst_offset (
    id SMALLSERIAL PRIMARY KEY,
    name CHAR(1) UNIQUE NOT NULL,
    CHECK (name IN ('E', 'A', 'S', 'O', 'Z', 'N', 'U'))
);

CREATE TABLE flights (
    id SERIAL PRIMARY KEY,
    airplane_id INTEGER NOT NULL,
    origin INTEGER NOT NULL,
    destination INTEGER NOT NULL,
    departure_date_time TIMESTAMP NOT NULL,
    scheduled_date_time TIMESTAMP NOT NULL,
    flight_number CHAR(7) UNIQUE NOT NULL,
    CHECK (departure_date_time <= scheduled_date_time),
    CHECK (origin <> destination)
);

CREATE TABLE aircraft_seating (
    id SERIAL PRIMARY KEY,
    aircraft_id INTEGER NOT NULL,
    seat_number INTEGER NOT NULL,
    seat_letter CHAR(1) NOT NULL,
    travel_class_id SMALLINT NOT NULL,
    UNIQUE (aircraft_id, seat_number, seat_letter)
);

CREATE TABLE aircrew (
    id SERIAL PRIMARY KEY,
    flight_id INTEGER,
    role_id INTEGER NOT NULL,
    employee_id INTEGER,
    UNIQUE (flight_id, employee_id)
);

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    gender_id SMALLINT,
    birth_date DATE NOT NULL,
    country_id INTEGER NOT NULL,
    hire_date DATE DEFAULT current_date,
    is_active BOOLEAN DEFAULT true,
    CHECK (birth_date <= current_date)
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE aircraft (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    length_mm INTEGER,
    wingspan_mm INTEGER,
    max_speed_kmh INTEGER,
    range_km INTEGER,
    manufacturer_id INTEGER NOT NULL,
    weight_kg NUMERIC NOT NULL,
    height_m NUMERIC NOT NULL,
    cruise_speed_kmh NUMERIC NOT NULL,
    max_fuel_capacity_l NUMERIC NOT NULL,
    CHECK (length_mm > 0),
    CHECK (wingspan_mm > 0),
    CHECK (max_speed_kmh > 0),
    CHECK (range_km > 0),
    CHECK (weight_kg > 0),
    CHECK (height_m > 0),
    CHECK (cruise_speed_kmh > 0),
    CHECK (max_fuel_capacity_l > 0)
);

CREATE TABLE manufacturers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE travel_classes (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE statuses_scope (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE statuses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    scope_id INTEGER NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE flight_seating (
    id SERIAL PRIMARY KEY,
    flight_id INTEGER NOT NULL,
    aircraft_seating INTEGER NOT NULL,
    passenger INTEGER NOT NULL
);

CREATE TABLE flight_status_history (
    id SERIAL PRIMARY KEY,
    flight_id INTEGER,
    status_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    ended_at TIMESTAMP,
    changed_by INTEGER NOT NULL,
    remarks VARCHAR(255)
);

CREATE TABLE airplanes (
                           id SERIAL PRIMARY KEY,
                           aircraft_id INTEGER NOT NULL,
                           registration_number VARCHAR(20) UNIQUE NOT NULL,
                           in_service BOOLEAN DEFAULT TRUE,
                           purchase_date DATE,
                           CHECK (registration_number ~ '^[A-Z0-9-]+$'),
                           CHECK (purchase_date <= current_date)
);

ALTER TABLE countries
    ADD CONSTRAINT countries_continent_fk FOREIGN KEY (continent_id) REFERENCES continents(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE cities
    ADD CONSTRAINT cities_country_fk FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE passengers
    ADD CONSTRAINT passengers_gender_id_fk FOREIGN KEY (gender_id) REFERENCES genders(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT passengers_country_id_fk FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE airports
    ADD CONSTRAINT airports_city_fk FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT airports_dst_offset_fk FOREIGN KEY (dst_offset_id) REFERENCES dst_offset(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE flights
    ADD CONSTRAINT flights_airplane_fk FOREIGN KEY (airplane_id) REFERENCES airplanes(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT flights_origin_fk FOREIGN KEY (origin) REFERENCES airports(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT flights_destination_fk FOREIGN KEY (destination) REFERENCES airports(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE aircraft_seating
    ADD CONSTRAINT aircraft_seating_aircraft_fk FOREIGN KEY (aircraft_id) REFERENCES aircraft(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT aircraft_seating_travel_class_fk FOREIGN KEY (travel_class_id) REFERENCES travel_classes(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE aircrew
    ADD CONSTRAINT aircrew_flight_id_fk FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT aircrew_role_id_fk FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT aircrew_employee_id_fk FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE employees
    ADD CONSTRAINT employees_gender_id_fk FOREIGN KEY (gender_id) REFERENCES genders(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT employees_country_id_fk FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE aircraft
    ADD CONSTRAINT aircraft_manufacturer_fk FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE flight_seating
    ADD CONSTRAINT flight_seating_flight_fk FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT flight_seating_aircraft_seating_fk FOREIGN KEY (aircraft_seating) REFERENCES aircraft_seating(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT flight_seating_passenger_fk FOREIGN KEY (passenger) REFERENCES passengers(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE flight_status_history
    ADD CONSTRAINT flight_status_history_flight_fk FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT flight_status_history_status_fk FOREIGN KEY (status_id) REFERENCES statuses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT flight_status_history_changed_by_fk FOREIGN KEY (changed_by) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE statuses
    ADD CONSTRAINT statuses_scope_fk FOREIGN KEY (scope_id) REFERENCES statuses_scope(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE airplanes
    ADD CONSTRAINT airplanes_aircraft_fk FOREIGN KEY (aircraft_id) REFERENCES aircraft(id) ON DELETE CASCADE ON UPDATE CASCADE;
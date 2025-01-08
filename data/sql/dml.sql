-- The following script imports data from the CSV files located in the data/datasets into a PostgreSQL database.
-- This file contains DML statements for the airlines database.
-- The information contained in this file, although it may be true in the CSV files, is not considered to be true.
-- Additionally, some data is discarded while other data will be generated automatically.
-- PostgreSQL version: 17

-- Temporary tables
CREATE TEMP TABLE temp_airports (
                                    airport_id INTEGER,
                                    name TEXT,
                                    city TEXT,
                                    country TEXT,
                                    iata TEXT,
                                    icao TEXT,
                                    latitude TEXT,
                                    longitude TEXT,
                                    altitude TEXT,
                                    timezone TEXT,
                                    dstOffset TEXT,
                                    tz TEXT,
                                    type TEXT,
                                    source TEXT
);

CREATE TEMP TABLE temp_airlines (
                                    passenger_id TEXT,
                                    first_name TEXT,
                                    last_name TEXT,
                                    gender TEXT,
                                    age INTEGER,
                                    nationality TEXT,
                                    airport_name TEXT,
                                    airport_country_code TEXT,
                                    country_name TEXT,
                                    airport_continent TEXT,
                                    continents TEXT,
                                    departure_date TEXT,
                                    arrival_airport TEXT,
                                    pilot_name TEXT,
                                    flight_status TEXT
);

-- Load data from CSV files
COPY temp_airports
    FROM 'D:\Users\ezemg\Repositories\airlines\data\datasets\airports.csv'
    DELIMITER ','
    CSV HEADER
    ENCODING 'WIN1252';

COPY temp_airlines
    FROM 'D:\Users\ezemg\Repositories\airlines\data\datasets\airline.csv'
    DELIMITER ','
    CSV HEADER
    ENCODING 'UTF8';

-- Insert data from the tables
INSERT INTO continents (name, code)
SELECT DISTINCT lines.continents, lines.airport_continent
FROM temp_airlines lines;

INSERT INTO countries (name, code, continent_id)
SELECT DISTINCT temp_airlines.country_name, temp_airlines.airport_country_code, continents.id
FROM temp_airlines JOIN temp_airports ON temp_airlines.country_name = temp_airports.country
                   JOIN continents ON temp_airlines.continents = continents.name
WHERE temp_airlines.country_name IS NOT NULL
  AND temp_airlines.airport_country_code IS NOT NULL
  AND temp_airlines.airport_continent IS NOT NULL
ON CONFLICT (name, code) DO NOTHING;

INSERT INTO dst_offset (name)
SELECT DISTINCT ports.dstOffset
FROM temp_airports ports
WHERE ports.dstOffset != '\N';

INSERT INTO genders (name)
SELECT DISTINCT temp_airlines.gender
FROM temp_airlines
WHERE temp_airlines.gender != '\N';

INSERT INTO cities (name, country_id)
SELECT DISTINCT ports.city, c.id
FROM temp_airports ports
         JOIN countries c ON ports.country = c.name
WHERE ports.city IS NOT NULL
  AND ports.country IS NOT NULL
  AND ports.city != '\N'
ON CONFLICT (name, country_id) DO NOTHING;

INSERT INTO airports (name, city_id, iata, icao, latitude, longitude, altitude, utc, dst_offset_id, timezone)
SELECT DISTINCT ON (ports.icao, cities.id)
    ports.name, cities.id,
    NULLIF(ports.iata, '\N'), NULLIF(ports.icao, '\N'),
    ports.latitude::REAL, ports.longitude::REAL,
    ports.altitude::REAL,
    NULLIF(ports.timezone, '\N')::REAL, dst_offset.id,
    NULLIF(ports.tz, '\N')
FROM temp_airports ports
         JOIN cities ON ports.city = cities.name
         JOIN dst_offset ON ports.dstOffset = dst_offset.name
WHERE ports.icao IS NOT NULL
  AND ports.city != '\N'
ON CONFLICT DO NOTHING;

INSERT INTO passengers (first_name, last_name, gender_id, birth_date, country_id)
SELECT DISTINCT NULLIF(temp_airlines.first_name, '\N'), NULLIF(temp_airlines.last_name, '\N'), genders.id, (SELECT CURRENT_DATE - INTERVAL '1 year' * temp_airlines.age), countries.id
FROM temp_airlines JOIN genders ON temp_airlines.gender = genders.name
                   JOIN countries ON temp_airlines.country_name = countries.name
WHERE temp_airlines.first_name IS NOT NULL
  AND temp_airlines.last_name IS NOT NULL
  AND temp_airlines.age IS NOT NULL;

DROP TABLE temp_airports;
DROP TABLE temp_airlines;

-- Hardcoded data
INSERT INTO manufacturers (name) VALUES ('Boeing'), ('Airbus'), ('Embraer');

INSERT INTO aircraft (
    name,
    length_mm,
    wingspan_mm,
    max_speed_kmh,
    range_km,
    manufacturer_id,
    weight_kg,
    height_m,
    cruise_speed_kmh,
    max_fuel_capacity_l
) VALUES
      ('737 MAX 9A', 42160, 35900, 839, 6110, 1, 41400, 12.3, 830, 20891),
      ('737 MAX 9B', 39520, 35900, 839, 6480, 1, 41200, 12.3, 830, 20891),
      ('737 MAX 8', 39520, 35920, 842, 6570, 1, 41100, 12.3, 828, 20891),
      ('737-800', 39500, 35800, 842, 5765, 1, 41000, 12.5, 828, 20891),
      ('737-700', 33600, 35800, 842, 6370, 1, 33300, 12.5, 828, 20891),
      ('A330-200', 58820, 60300, 871, 13450, 2, 120000, 16.8, 871, 139090),
      ('A350', 66800, 64750, 945, 15000, 2, 130000, 17.1, 945, 141200),
      ('A330-300', 63660, 60300, 871, 11750, 2, 122000, 16.8, 871, 139090),
      ('A321', 44510, 35800, 876, 5950, 2, 46000, 11.8, 840, 23857),
      ('E-190', 36240, 28720, 870, 4537, 3, 28000, 10.6, 870, 12970),
      ('ERJ-175', 31680, 28650, 829, 3704, 3, 22000, 9.9, 820, 11000),
      ('E-195', 41500, 35100, 870, 4815, 3, 29000, 10.6, 870, 12970);



INSERT INTO travel_classes (name) VALUES ('Economy'), ('Business'), ('First'), ('Premium Economy');

DELETE
FROM airports a
WHERE a.name LIKE '%Duplicate%';

INSERT INTO statuses_scope (name) VALUES ('Flight');

INSERT INTO statuses (name, scope_id, description) VALUES
                                                       ('Scheduled', 1, 'The flight is scheduled to depart on the specified date and time.'),
                                                       ('Boarding', 1, 'Passengers are currently boarding the aircraft.'),
                                                       ('Closed', 1, 'The flight has closed its doors and is ready for departure.'),
                                                       ('Rescheduled', 1, 'The flight has been rescheduled to a different date and time.'),
                                                       ('Canceled', 1, 'The flight has been canceled and will not depart.'),
                                                       ('Completed', 1, 'The flight has reached its final destination and is considered finished.'),
                                                       ('TakingOff', 1, 'The flight is on the runway and in the process of taking off.'),
                                                       ('InAir', 1, 'The flight is currently airborne and en route to its destination.'),
                                                       ('Landed', 1, 'The flight has successfully landed at its destination airport.'),
                                                       ('InZone', 1, 'The flight is in the holding area or approach zone before landing.');

-- Random data
-- Functions
CREATE OR REPLACE FUNCTION generate_unique_registration() RETURNS VARCHAR AS $$
DECLARE
    reg_number VARCHAR(20);
BEGIN
    LOOP
        reg_number := 'N' || (random() * 99999 + 10000)::int;
        EXIT WHEN NOT EXISTS (SELECT 1 FROM airplanes WHERE registration_number = reg_number);
    END LOOP;
    RETURN reg_number;
END;
$$ LANGUAGE plpgsql;

-- Insertions
DO $$
    BEGIN
        FOR i IN 1..531 LOOP
                INSERT INTO airplanes (aircraft_id, registration_number, in_service, purchase_date)
                VALUES (
                           (random() * 11 + 1)::int,
                           generate_unique_registration(),
                           (random() > 0.5),
                           date '2017-01-01' + (random() * (current_date - date '2017-01-01'))::int
                       );
            END LOOP;
    END;
$$;
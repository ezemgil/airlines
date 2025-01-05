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
    dst TEXT,
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
ENCODING 'LATIN1';

COPY temp_airlines
FROM 'D:\Users\ezemg\Repositories\airlines\data\datasets\airline.csv'
DELIMITER ','
CSV HEADER
ENCODING 'LATIN1';

-- Insert data from the tables
INSERT INTO statuses (name)
SELECT DISTINCT temp_airlines.flight_status
FROM temp_airlines;

INSERT INTO continents (name, code)
SELECT DISTINCT lines.continents, lines.airport_continent
FROM temp_airlines lines;

INSERT INTO dst (name)
SELECT DISTINCT ports.dst
FROM temp_airports ports
WHERE ports.dst != '\N';

INSERT INTO timezones (value)
SELECT DISTINCT ports.tz
FROM temp_airports ports
WHERE ports.dst != '\N';

INSERT INTO genders (name)
SELECT DISTINCT temp_airlines.gender
FROM temp_airlines
WHERE temp_airlines.gender != '\N';

INSERT INTO cities (name, country_id)
SELECT DISTINCT ports.city, c.id
FROM temp_airports ports JOIN temp_airlines lines ON ports.country = lines.nationality
JOIN countries c ON ports.country = c.name;

INSERT INTO countries (name, code, continent_id)
SELECT DISTINCT temp_airlines.country_name, temp_airlines.airport_country_code, continents.id
FROM temp_airlines JOIN temp_airports ON temp_airlines.country_name = temp_airports.country
                    JOIN continents ON temp_airlines.continents = continents.name
WHERE temp_airlines.country_name IS NOT NULL
  AND temp_airlines.airport_country_code IS NOT NULL
  AND temp_airlines.airport_continent IS NOT NULL
  ON CONFLICT (name, code) DO NOTHING;


INSERT INTO airports (name, city_id, iata, icao, latitude, longitude, altitude, utc, dst_id, timezone_id)
SELECT DISTINCT ports.name, cities.id, NULLIF(ports.iata, '\N'), NULLIF(ports.icao, '\N'), ports.latitude::REAL, ports.longitude::REAL,
				ports.altitude::REAL, NULLIF(ports.timezone, '\N')::REAL, dst.id, timezones.id
FROM temp_airports ports
JOIN cities ON ports.city = cities.name
JOIN dst ON ports.dst = dst.name
JOIN timezones ON ports.tz = timezones.value;

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

INSERT INTO aircraft (name, length_mm, wingspan_mm, max_speed_kmh, range_km, manufacturer_id, tail_number) VALUES
('737 MAX 9A', 42160, 35900, 839, 6110, 1, 'LV-FUA'),
('737 MAX 9B', 39520, 35900, 839, 6480, 1, 'LV-FUB'),
('737 MAX 8', 39520, 35920, 842, 6570, 1, 'LV-FUC'),
('737-800', 39500, 35800, 842, 5765, 1, 'LV-FUD'),
('737-700', 33600, 35800, 842, 6370, 1, 'LV-FUE'),
('A330-200', 58820, 60300, 871, 13450, 2, 'LV-FUF'),
('A350', 66800, 64750, 945, 15000, 2, 'LV-FUG'),
('A330-300', 63660, 60300, 871, 11750, 2, 'LV-FUH'),
('A321', 44510, 35800, 876, 5950, 2, 'LV-FUI'),
('E-190', 36240, 28720, 870, 4537, 3, 'LV-FUJ'),
('ERJ-175', 31680, 28650, 829, 3704, 3, 'LV-FUK'),
('E-195', 41500, 35100, 870, 4815, 3, 'LV-FUL');

INSERT INTO travel_classes (name) VALUES ('Economy'), ('Business'), ('First'), ('Premium Economy');

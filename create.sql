CREATE TABLE cars (carId VARCHAR(255) NOT NULL, color VARCHAR(20), engine VARCHAR(20), PRIMARY KEY (carId));
CREATE TABLE seats (seatId VARCHAR(255) NOT NULL, seatMaterial SMALLINT, model VARCHAR(20), PRIMARY KEY (seatId));
CREATE TABLE stearing (stearingId VARCHAR(255) NOT NULL, stearingType VARCHAR(20), PRIMARY KEY (stearingId));

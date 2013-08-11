/***********************************
 * Constraint coverage for Flights *
 ***********************************/
DROP TABLE IF EXISTS FlightAvailable;
DROP TABLE IF EXISTS Flights;
CREATE TABLE Flights (
	FLIGHT_ID	CHAR(6)	NOT NULL,
	SEGMENT_NUMBER	INT	NOT NULL,
	ORIG_AIRPORT	CHAR(3),
	DEPART_TIME	TIME,
	DEST_AIRPORT	CHAR(3),
	ARRIVE_TIME	TIME,
	MEAL	CHAR(1)	NOT NULL,
	PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER),
	CONSTRAINT MEAL_CONSTRAINT CHECK (MEAL IN ('B', 'L', 'D', 'S'))
);
CREATE TABLE FlightAvailable (
	FLIGHT_ID	CHAR(6)	NOT NULL,
	SEGMENT_NUMBER	INT	NOT NULL,
	FLIGHT_DATE	DATE	NOT NULL,
	ECONOMY_SEATS_TAKEN	INT,
	BUSINESS_SEATS_TAKEN	INT,
	FIRSTCLASS_SEATS_TAKEN	INT,
	CONSTRAINT FLTAVAIL_PK PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER),
	CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER) REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)
);
-- Coverage: 20/20 (100.00000%) 
-- Time to generate: 910ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 204ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('a', 0, '', '00:00:00', '', '00:00:00', 'B');
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B');
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('a', 0, '1000-01-01', 0, 0, 0);
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 87
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]" on table "Flights"
-- * Success: true
-- * Time: 20ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B');
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(FLIGHT_ID)" on table "Flights"
-- * Success: true
-- * Time: 11ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES(NULL, 0, '', '00:00:00', '', '00:00:00', 'B');
-- * Number of objective function evaluations: 31
-- * Number of restarts: 0

-- Negating "NOT NULL(SEGMENT_NUMBER)" on table "Flights"
-- * Success: true
-- * Time: 14ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', NULL, '', '00:00:00', '', '00:00:00', 'B');
-- * Number of objective function evaluations: 30
-- * Number of restarts: 0

-- Negating "NOT NULL(MEAL)" on table "Flights"
-- * Success: true
-- * Time: 27ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 1, '', '00:00:00', '', '00:00:00', NULL);
-- * Number of objective function evaluations: 28
-- * Number of restarts: 0

-- Negating "CHECK[MEAL IN ('B', 'L', 'D', 'S')]" on table "Flights"
-- * Success: true
-- * Time: 8ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 1, '', '00:00:00', '', '00:00:00', '');
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]" on table "FlightAvailable"
-- * Success: true
-- * Time: 67ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 1, '', '00:00:00', '', '00:00:00', 'B');
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 35
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]" on table "FlightAvailable"
-- * Success: true
-- * Time: 75ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('b', 0, '', '00:00:00', '', '00:00:00', 'B');
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 3, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 56
-- * Number of restarts: 0

-- Negating "NOT NULL(FLIGHT_ID)" on table "FlightAvailable"
-- * Success: true
-- * Time: 51ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', -1, '', '00:00:00', '', '00:00:00', 'B');
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES(NULL, 0, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 44
-- * Number of restarts: 0

-- Negating "NOT NULL(SEGMENT_NUMBER)" on table "FlightAvailable"
-- * Success: true
-- * Time: 294ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('ghayrv', 96, 'pno', '07:22:58', 'aeh', '08:41:00', 'B');
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('ghayrv', NULL, '2007-01-13', 45, 96, 9);
-- * Number of objective function evaluations: 312
-- * Number of restarts: 1

-- Negating "NOT NULL(FLIGHT_DATE)" on table "FlightAvailable"
-- * Success: true
-- * Time: 139ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('ysrdla', -86, 'upx', '17:31:02', 'fva', '15:45:42', 'S');
INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 1, NULL, -66, 92, 44);
-- * Number of objective function evaluations: 203
-- * Number of restarts: 1


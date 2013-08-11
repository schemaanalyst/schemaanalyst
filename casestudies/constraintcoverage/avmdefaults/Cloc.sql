/********************************
 * Constraint coverage for Cloc *
 ********************************/
DROP TABLE IF EXISTS t;
DROP TABLE IF EXISTS metadata;
CREATE TABLE metadata (
	timestamp	VARCHAR(50),
	Project	VARCHAR(50),
	elapsed_s	INT
);
CREATE TABLE t (
	Project	VARCHAR(50),
	Language	VARCHAR(50),
	File	VARCHAR(50),
	nBlank	INT,
	nComment	INT,
	nCode	INT,
	nScaled	INT
);
-- Coverage: 0/0 (0%) 
-- Time to generate: 1ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 1ms 
INSERT INTO metadata(timestamp, Project, elapsed_s) VALUES('', '', 0);
INSERT INTO metadata(timestamp, Project, elapsed_s) VALUES('', '', 0);
INSERT INTO t(Project, Language, File, nBlank, nComment, nCode, nScaled) VALUES('', '', '', 0, 0, 0, 0);
INSERT INTO t(Project, Language, File, nBlank, nComment, nCode, nScaled) VALUES('', '', '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0


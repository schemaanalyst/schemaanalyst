CREATE TABLE schemaanalyst_activemutant(id text);
UPDATE schemaanalyst_activemutant SET id = ID;

-- Primary key testing
CREATE TABLE one (a int, b int, primary key (a));

CREATE TABLE one (a int, b int);
ALTER TABLE one ADD CHECK (satisfy_notnull(a::text) AND satisfy_pk('one'::text,'a'::text,a::text));

INSERT INTO one VALUES (null); -- FAIL
INSERT INTO one VALUES (1); -- PASS
INSERT INTO one VALUES (1); -- FAIL
INSERT INTO one VALUES (2); -- PASS

-- Primary key (multi) testing
CREATE TABLE one (a int, b int, primary key (a,b));

CREATE TABLE one (a int, b int);
ALTER TABLE one ADD CHECK (satisfy_notnull(a::text) AND satisfy_notnull(b::text) AND satisfy_pk('one'::text,'{a,b}'::text[],ARRAY[a,b]::text[]));

INSERT INTO one VALUES (null,null); -- FAIL
INSERT INTO one VALUES (null,null); -- FAIL
INSERT INTO one VALUES (1,null); -- FAIL
INSERT INTO one VALUES (1,null); -- FAIL
INSERT INTO one VALUES (null,1); -- FAIL
INSERT INTO one VALUES (null,1); -- FAIL
INSERT INTO one VALUES (1,1); -- PASS
INSERT INTO one VALUES (1,1); -- FAIL
INSERT INTO one VALUES (1,2); -- PASS
INSERT INTO one VALUES (1,2); -- FAIL
INSERT INTO one VALUES (2,1); -- PASS
INSERT INTO one VALUES (2,2); -- PASS
INSERT INTO one VALUES (2,2); -- FAIL

-- Unique testing
CREATE TABLE one (a int, b int, unique(a));

CREATE TABLE one (a int, b int);
ALTER TABLE one ADD CHECK (NOT satisfy_notnull(a::text) OR satisfy_unique('one'::text,'a'::text,a::text));

INSERT INTO one VALUES (null); -- PASS
INSERT INTO one VALUES (1); -- PASS
INSERT INTO one VALUES (1); -- FAIL
INSERT INTO one VALUES (2); -- PASS

-- Unique (multi) testing
CREATE TABLE one (a int, b int, unique(a,b));

CREATE TABLE one (a int, b int);
ALTER TABLE one ADD CHECK (NOT satisfy_notnull(a::text) OR NOT satisfy_notnull(b::text) OR satisfy_unique('one'::text,'{a,b}'::text[],ARRAY[a,b]::text[]));

INSERT INTO one VALUES (null,null); -- PASS
INSERT INTO one VALUES (null,null); -- PASS
INSERT INTO one VALUES (1,null); -- PASS
INSERT INTO one VALUES (1,null); -- PASS
INSERT INTO one VALUES (null,1); -- PASS
INSERT INTO one VALUES (null,1); -- PASS
INSERT INTO one VALUES (1,1); -- PASS
INSERT INTO one VALUES (1,1); -- FAIL
INSERT INTO one VALUES (1,2); -- PASS
INSERT INTO one VALUES (1,2); -- FAIL
INSERT INTO one VALUES (2,1); -- PASS
INSERT INTO one VALUES (2,2); -- PASS
INSERT INTO one VALUES (2,2); -- FAIL

-- Not null testing
CREATE TABLE one (a int not null, b int);

CREATE TABLE one (a int, b int);
ALTER TABLE one ADD CHECK (satisfy_notnull(a::text));

INSERT INTO one VALUES (null); -- FAIL
INSERT INTO one VALUES (1); -- PASS

-- Foreign key testing
CREATE TABLE one (a int, b int, unique(a));
CREATE TABLE two (c int, d int, foreign key (c) references one(a));

CREATE TABLE one (a int, b int, unique(a));
CREATE TABLE two (c int, d int);
ALTER TABLE two ADD CHECK (NOT satisfy_notnull(c::text) OR satisfy_fk('one'::text,'a',c::text));

INSERT INTO two VALUES (null); -- PASS
INSERT INTO two VALUES (1); -- FAIL
INSERT INTO one VALUES (null); -- PASS
INSERT INTO two VALUES (null); -- PASS
INSERT INTO two VALUES (1); -- FAIL
INSERT INTO one VALUES (1); -- PASS
INSERT INTO two VALUES (null); -- PASS
INSERT INTO two VALUES (1); -- PASS

-- Foreign key (multi) testing 
CREATE TABLE one (a int, b int, unique(a,b));
CREATE TABLE two (c int, d int, foreign key (c,d) references one(a,b));

CREATE TABLE one (a int, b int, unique(a,b));
CREATE TABLE two (c int, d int);
ALTER TABLE two ADD CHECK (NOT satisfy_notnull(c::text) OR NOT satisfy_notnull(d::text) OR satisfy_fk('one'::text,'{a,b}'::text[],ARRAY[c,d]::text[]));

INSERT INTO two VALUES (null, null); -- PASS
INSERT INTO two VALUES (null, 1); -- PASS
INSERT INTO two VALUES (1, null); -- PASS
INSERT INTO two VALUES (1, 1); -- FAIL
INSERT INTO one VALUES (null, null); -- PASS
INSERT INTO two VALUES (null, null); -- PASS
INSERT INTO two VALUES (null, 1); -- PASS
INSERT INTO two VALUES (1, null); -- PASS
INSERT INTO two VALUES (1, 1); -- FAIL
INSERT INTO one VALUES (1, 1); -- PASS
INSERT INTO two VALUES (null, null); -- PASS
INSERT INTO two VALUES (null, 1); -- PASS
INSERT INTO two VALUES (1, null); -- PASS
INSERT INTO two VALUES (1, 1); -- PASS

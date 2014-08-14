-- PRIMARY KEY constraint
CREATE OR REPLACE FUNCTION satisfy_pk(_table text, _column text, _value text) RETURNS boolean AS
$$
DECLARE
result integer;
BEGIN
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %I=%s))::int', _table,_column,_value::text) INTO result;
RETURN NOT result::boolean;
END
$$
LANGUAGE plpgsql;

-- PRIMARY KEY constraint (multi-column)
CREATE OR REPLACE FUNCTION satisfy_pk(_table text, _columns text[], _values text[]) RETURNS boolean AS
$$
DECLARE 
result integer;
condition text;
BEGIN
SELECT INTO condition string_agg(format('%I = %L', _columns[i], _values[i]),' AND ' ORDER BY i) FROM generate_subscripts(_columns, 1) i;
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %s))::int', _table, condition) INTO result;
RETURN NOT result::boolean;
END
$$
LANGUAGE plpgsql;

-- UNIQUE constraint
CREATE OR REPLACE FUNCTION satisfy_unique(_table text, _column text, _value text) RETURNS boolean AS
$$
DECLARE
result integer;
BEGIN
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %I=%s))::int', _table,_column,_value::text) INTO result;
RETURN NOT result::boolean;
END
$$
LANGUAGE plpgsql;

-- UNIQUE constraint multi-column
CREATE OR REPLACE FUNCTION satisfy_unique(_table text, _columns text[], _values text[]) RETURNS boolean AS
$$
DECLARE 
result integer;
condition text;
BEGIN
SELECT INTO condition string_agg(format('%I = %L', _columns[i], _values[i]),' AND ' ORDER BY i) FROM generate_subscripts(_columns, 1) i;
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %s))::int', _table, condition) INTO result;
RETURN NOT result::boolean;
END
$$
LANGUAGE plpgsql;

-- FOREIGN KEY constraint
CREATE OR REPLACE FUNCTION satisfy_fk(_table text, _column text, _value text) RETURNS boolean AS
$$
DECLARE
result integer;
BEGIN
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %I=%s))::int', _table,_column,_value::text) INTO result;
RETURN result::boolean;
END
$$
LANGUAGE plpgsql;

-- FOREIGN KEY constraint multi-column
CREATE OR REPLACE FUNCTION satisfy_fk(_table text, _columns text[], _values text[]) RETURNS boolean AS
$$
DECLARE 
result integer;
condition text;
BEGIN
SELECT INTO condition string_agg(format('%I = %L', _columns[i], _values[i]),' AND ' ORDER BY i) FROM generate_subscripts(_columns, 1) i;
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %s))::int', _table, condition) INTO result;
RETURN result::boolean;
END
$$
LANGUAGE plpgsql;

-- NOT NULL constraint
CREATE OR REPLACE FUNCTION satisfy_notnull(_value text) RETURNS boolean AS
$$
BEGIN
RETURN _value IS NOT NULL;
END
$$
LANGUAGE plpgsql;

-- Active mutant
CREATE OR REPLACE FUNCTION active_mutant(_id text) RETURNS boolean AS
$$
DECLARE
result integer;
BEGIN
-- UPDATE schemaanalyst_activecalls SET count = count + 1;
EXECUTE format('SELECT (EXISTS (SELECT 1 FROM %I WHERE %I = %L))::int','schemaanalyst_activemutant','id',_id) INTO result;
RETURN result::boolean;
END
-- BEGIN
-- UPDATE schemaanalyst_activecalls SET count = count + 1;
-- RETURN 'false'::boolean;
-- END
$$
LANGUAGE plpgsql;

-- Switch mutant
CREATE OR REPLACE FUNCTION activate_mutant(_id int) RETURNS int AS
$$
BEGIN
UPDATE schemaanalyst_activemutant SET id = _id;
RETURN _id;
END
$$
LANGUAGE plpgsql;

-- Assert statement will pass
CREATE OR REPLACE FUNCTION assertPass(_statement text) RETURNS void AS
$$
DECLARE
i int;
BEGIN
EXECUTE(_statement);
EXCEPTION
WHEN integrity_constraint_violation OR check_violation THEN
RAISE EXCEPTION 'TEST CASE FAILED: Expected to pass, but failed';
END
$$
LANGUAGE plpgsql;

-- Assert statement will fail
CREATE OR REPLACE FUNCTION assertFail(_statement text) RETURNS void AS
$$
DECLARE
i int;
BEGIN
EXECUTE(_statement);
RAISE EXCEPTION 'TEST CASE FAILED: Expected to fail, but passed';
EXCEPTION
WHEN integrity_constraint_violation OR check_violation THEN
END
$$
LANGUAGE plpgsql;

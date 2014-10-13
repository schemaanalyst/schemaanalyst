-- Primary key
CHECK (NOT active_mutant(ID) OR (satisfy_notnull(VALUE::text) AND satisfy_pk('TABLE'::text,'COLUMN'::text,VALUE::text)));

-- Primary key multi-column
CHECK (NOT active_mutant(ID) OR (satisfy_notnull(VALUE_1::text) AND ... AND satisfy_notnull(VALUE_N::text) AND satisfy_pk('TABLE'::text,'{COLUMN_1,..,COLUMN_N}'::text[],ARRAY[VALUE_1,..,VALUE_N]::text[])));

-- Unique
CHECK (NOT active_mutant(ID) OR (NOT satisfy_notnull(VALUE::text) OR satisfy_unique('TABLE'::text,'COLUMN'::text,VALUE::text)));

-- Unique key multi-column
CHECK (NOT active_mutant(ID) OR (NOT satisfy_notnull(VALUE_1::text) OR ... OR NOT satisfy_notnull(VALUE_N::text) OR satisfy_unique('TABLE'::text,'{COLUMN_1,..,COLUMN_N}'::text[],ARRAY[VALUE_1,..,VALUE_N]::text[])));

-- Not Null
CHECK (NOT active_mutant(ID) OR (satisfy_notnull(VALUE::text)));

-- Foreign key
CHECK (NOT active_mutant(ID) OR (NOT satisfy_notnull(VALUE::text) OR satisfy_fk('REFTABLE'::text,'REFCOLUMN',VALUE::text)));

-- Foreign key multi-column
CHECK (NOT active_mutant(ID) OR (NOT satisfy_notnull(VALUE_1::text) OR ... OR NOT satisfy_notnull(VALUE_N::text) OR satisfy_fk('REFTABLE'::text,'{REFCOLUMN_1,..,REFCOLUMN_N}'::text[],ARRAY[VALUE_1,..,VALUE_N]::text[])));


-- alter table tablename rename to oldtablename;
-- create table tablename (column defs go here);
-- insert into tablename (col1, col2, col3) select col2, col1, col3 from oldtablename;
-- SELECT setval(pg_get_serial_sequence('tablename', 'id'), coalesce(max(id), 0)+1 , false) FROM tablename;
-- DROP TABLE IF EXISTS oldtablename;

set search_path = milkyway;

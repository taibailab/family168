
-- drop
select 'alter table ' || c.table_name || ' drop constraint ' ||
       c.constraint_name || ';' as sql
  from dba_constraints c
 where c.owner = 'FILOS' and c.constraint_type = 'R'
 order by sql;


-- add
select distinct 'alter table ' || c1.table_name || ' add constraint ' ||
                c1.constraint_name || ' foreign key(' || cc1.column_name ||
                ') references ' || c2.table_name || '(' || cc2.column_name || ');' as sql
  from dba_constraints  c1,
       dba_constraints  c2,
       dba_cons_columns cc1,
       dba_cons_columns cc2
 where c1.owner = 'FILOS' and c1.constraint_type = 'R' and
       c2.owner = 'FILOS' and c1.r_constraint_name = c2.constraint_name and
       c1.constraint_name = cc1.constraint_name and
       c2.constraint_name = cc2.constraint_name
 order by sql;

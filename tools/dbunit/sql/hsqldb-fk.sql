
-- drop
select 'alter table ' || t.table_name || ' drop constraint ' ||
       t.constraint_name || ';' as sql
  from information_schema.system_table_constraints t
 where t.constraint_schema = 'PUBLIC' and t.constraint_type='FOREIGN KEY'
 order by sql;

-- add
select 'alter table ' || t.fktable_name || ' add constraint ' ||
        t.fk_name || ' foreign key(' || t.fkcolumn_name ||
        ') references ' || t.pktable_name || '(' || t.pkcolumn_name || ');' as sql
 from information_schema.system_crossreference t;


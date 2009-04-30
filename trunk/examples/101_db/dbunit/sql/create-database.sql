

--alter table father drop constraint fk_father_mother;
--alter table mother drop constraint fk_mother_father;


drop table mother if exists;
drop table father if exists;


create table father(
    id bigint,
    name varchar(100)
);
alter table father add constraint pk_father primary key(id);
insert into father(id,name) values(1,'father');


create table mother(
    id bigint,
    name varchar(100),
    father_id bigint
);
alter table mother add constraint pk_mother primary key(id);
alter table mother add constraint fk_mother_father foreign key(father_id) references father(id);
insert into mother(id,name,father_id) values(1,'mother',1);


alter table father add mother_id bigint;
alter table father add constraint fk_father_mother foreign key(mother_id) references mother(id);
insert into father(id,name,mother_id) values(2,'father_2',1);


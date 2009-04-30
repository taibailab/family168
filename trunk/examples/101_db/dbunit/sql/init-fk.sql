
alter table mother add constraint fk_mother_father foreign key(father_id) references father(id);
alter table father add constraint fk_father_mother foreign key(mother_id) references mother(id);


-- GENRES table
insert into GENRES
select *
from (select 1, 'Комедия'
     ) x
where (select count (*) from GENRES where GENRE_ID=1)=0;

insert into GENRES
select *
from (select 2, 'Драма'
     ) x
where (select count (*) from GENRES where GENRE_ID=2)=0;

insert into GENRES
select *
from (select 3, 'Мультфильм'
     ) x
where (select count (*) from GENRES where GENRE_ID=3)=0;

insert into GENRES
select *
from (select 4, 'Триллер'
     ) x
where (select count (*) from GENRES where GENRE_ID=4)=0;

insert into GENRES
select *
from (select 5, 'Документальный'
     ) x
where (select count (*) from GENRES where GENRE_ID=5)=0;

insert into GENRES
select *
from (select 6, 'Боевик'
     ) x
where (select count (*) from GENRES where GENRE_ID=6)=0;



-- RATINGS table

insert into RATINGS
select *
from (select 1, 'G'
     ) x
where (select count (*) from RATINGS where RATING_ID=1)=0;

insert into RATINGS
select *
from (select 2, 'PG'
     ) x
where (select count (*) from RATINGS where RATING_ID=2)=0;

insert into RATINGS
select *
from (select 3, 'PG-13'
     ) x
where (select count (*) from RATINGS where RATING_ID=3)=0;
insert into RATINGS
select *
from (select 4, 'R'
     ) x
where (select count (*) from RATINGS where RATING_ID=4)=0;

insert into RATINGS
select *
from (select 5, 'NC-17'
     ) x
where (select count (*) from RATINGS where RATING_ID=5)=0;


